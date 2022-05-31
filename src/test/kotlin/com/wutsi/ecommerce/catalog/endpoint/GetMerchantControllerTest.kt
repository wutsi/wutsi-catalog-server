package com.wutsi.ecommerce.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.ecommerce.catalog.dto.GetMerchantResponse
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/GetMerchantController.sql"])
class GetMerchantControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Test
    fun enabled() {
        val response = rest.getForEntity("http://localhost:$port/v1/merchants/100", GetMerchantResponse::class.java)

        assertEquals(200, response.statusCodeValue)

        val merchant = response.body!!.merchant
        assertEquals(1000, merchant.accountId)
        assertEquals(555, merchant.cityId)

        val metrics = merchant.overallMetrics
        assertEquals(1000, metrics.totalViews)
        assertEquals(1, metrics.totalShares)
        assertEquals(10, metrics.totalChats)
        assertEquals(100, metrics.totalOrders)
        assertEquals(10000, metrics.totalSales)
        assertEquals(0.5, metrics.score)
        assertEquals(0.1, metrics.conversion)
    }

    @Test
    fun disabled() {
        val ex = assertThrows<HttpClientErrorException> {
            rest.getForEntity("http://localhost:$port/v1/merchants/101", GetMerchantResponse::class.java)
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.MERCHANT_DISABLED.urn, response.error.code)
    }

    @Test
    fun notFound() {
        val ex = assertThrows<HttpClientErrorException> {
            rest.getForEntity("http://localhost:$port/v1/merchants/9999", GetMerchantResponse::class.java)
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.MERCHANT_NOT_FOUND.urn, response.error.code)
    }
}
