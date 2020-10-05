package com.radutopor.dogsshouldvote.business.errors.mappers

import com.radutopor.dogsshouldvote.webapi.models.ErrorApiResp
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.text.ParseException

class ApiErrorMapperTest {

    private lateinit var mapper: ApiErrorMapper

    private val errorApiResp = ErrorApiResp(
        status = "error",
        message = "some message",
        code = 404
    )

    @Before
    fun setUp() {
        mapper = ApiErrorMapper()
    }

    @Test
    fun `maps ErrorApiResp to ApiError`() {
        val apiError = mapper.map(errorApiResp)

        apiError.run {
            assertThat(code).isEqualTo(errorApiResp.code)
            assertThat(message).isEqualTo(errorApiResp.message)
        }
    }

    @Test(expected = ParseException::class)
    fun `if ErrorApiResp has no code, throw ParseException`() {
        val errorApiResp = errorApiResp.copy(code = null)

        mapper.map(errorApiResp)
    }
}