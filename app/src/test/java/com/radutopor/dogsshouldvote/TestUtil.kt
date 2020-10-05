package com.radutopor.dogsshouldvote

import org.mockito.internal.invocation.InterceptedInvocation
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.OngoingStubbing
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.startCoroutineUninterceptedOrReturn

/**
 * This should be replaced after the release of https://github.com/nhaarman/mockito-kotlin/pull/357
 */
@Suppress("UNCHECKED_CAST")
infix fun <T> OngoingStubbing<T>.willAnswer(answer: suspend (InvocationOnMock) -> T?): OngoingStubbing<T> {
    return thenAnswer {
        //all suspend functions/lambdas has Continuation as the last argument.
        //InvocationOnMock does not see last argument
        val rawInvocation = it as InterceptedInvocation
        val continuation = rawInvocation.rawArguments.last() as Continuation<T?>

        answer.startCoroutineUninterceptedOrReturn(it, continuation)
    }
}