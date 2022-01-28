package com.wutsi.platform.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.platform.catalog.dto.GetProductResponse
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpStatusCodeException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/GetProductController.sql"])
class GetProductControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Test
    fun get() {
        // WHEN
        val response = rest.getForEntity("http://localhost:$port/v1/products/100", GetProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val product = response.body!!.product
        assertEquals("Yo", product.title)
        assertEquals("Man", product.summary)
        assertEquals("This is the description", product.description)
        assertEquals(101L, product.categoryId)
        assertEquals(1000.0, product.price)
        assertEquals(1500.0, product.comparablePrice)
        assertEquals("XAF", product.currency)
        assertEquals(100, product.thumbnail?.id)
        assertEquals("https://img.com/1.png", product.thumbnail?.url)
        assertNotNull(product.created)
        assertNotNull(product.updated)

        val pictures = product.pictures
        assertEquals(2, pictures.size)
        assertEquals("https://img.com/1.png", pictures[0].url)
        assertEquals("https://img.com/2.png", pictures[1].url)
    }

    @Test
    fun notFound() {
        val ex = assertThrows<HttpStatusCodeException> {
            rest.getForEntity("http://localhost:$port/v1/products/99999", GetProductResponse::class.java)
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PRODUCT_NOT_FOUND.urn, response.error.code)
    }

    @Test
    fun deleted() {
        val ex = assertThrows<HttpStatusCodeException> {
            rest.getForEntity("http://localhost:$port/v1/products/900", GetProductResponse::class.java)
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PRODUCT_NOT_FOUND.urn, response.error.code)
    }
}
