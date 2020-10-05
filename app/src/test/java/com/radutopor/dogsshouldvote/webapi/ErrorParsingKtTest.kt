package com.radutopor.dogsshouldvote.webapi

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ErrorParsingKtTest {

    @Test
    fun `if HttpException has error body in parsable format, return ErrorApiResp`() {
        val status = "error"
        val message = "some message"
        val code = 404
        val responseString = "{\"status\":\"$status\",\"message\":\"$message\",\"code\":$code}"
        val responseBody = mock<ResponseBody>()
        whenever(responseBody.string()).thenReturn(responseString)
        val response = mock<Response<String>>()
        whenever(response.errorBody()).thenReturn(responseBody)
        val httpException = mock<HttpException>()
        whenever(httpException.response()).thenReturn(response)

        val errorApiResp = httpException.getErrorApiResp()

        assertThat(errorApiResp).isNotNull
        assertThat(errorApiResp!!.status).isEqualTo(status)
        assertThat(errorApiResp.message).isEqualTo(message)
        assertThat(errorApiResp.code).isEqualTo(code)
    }

    @Test
    fun `if HttpException has no error body, return null`() {
        val httpException = mock<HttpException>()

        val errorApiResp = httpException.getErrorApiResp()

        assertThat(errorApiResp).isNull()
    }
}