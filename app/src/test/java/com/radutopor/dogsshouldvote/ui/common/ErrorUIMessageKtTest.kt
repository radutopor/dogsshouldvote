package com.radutopor.dogsshouldvote.ui.common

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.radutopor.dogsshouldvote.R
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ErrorUIMessageKtTest {

    private val repoMessage = "oops"
    private val repo = mock<ResourceRepository>()

    @Before
    fun setUp() {
        whenever(repo.getString(R.string.oops)).thenReturn(repoMessage)
    }

    @Test
    fun `if error is HttpException and has ApiError, return its message`() {
        val message = "some message"
        val responseString = "{\"status\":\"success\",\"message\":\"$message\",\"code\":404}"
        val responseBody = mock<ResponseBody>()
        whenever(responseBody.string()).thenReturn(responseString)
        val response = mock<Response<String>>()
        whenever(response.errorBody()).thenReturn(responseBody)
        val exception = mock<HttpException>()
        whenever(exception.response()).thenReturn(response)

        val uiMessage = exception.getUIMessage(mock())

        assertThat(uiMessage).isEqualTo(message)
    }

    @Test
    fun `if error is HttpException but has no ApiError, return oops message using resourceRepo`() {
        val exception = mock<HttpException>()

        val uiMessage = exception.getUIMessage(repo)

        assertThat(uiMessage).isEqualTo(repoMessage)
    }

    @Test
    fun `if error is not HttpException, return oops message using resourceRepo`() {
        val exception = Exception()

        val uiMessage = exception.getUIMessage(repo)

        assertThat(uiMessage).isEqualTo(repoMessage)
    }
}