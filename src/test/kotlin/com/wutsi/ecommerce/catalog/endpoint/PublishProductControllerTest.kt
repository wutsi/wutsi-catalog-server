package com.wutsi.ecommerce.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.ecommerce.catalog.error.PublishError
import com.wutsi.ecommerce.catalog.event.EventURN
import com.wutsi.ecommerce.catalog.event.ProductEventPayload
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/PublishProductController.sql"])
class PublishProductControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: ProductRepository

    @Test
    fun publish() {
        // WHEN
        val response = rest.postForEntity(url(100), null, Any::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(100).get()
        assertEquals(ProductStatus.PUBLISHED, product.status)
        assertNotNull(product.published)

        verify(eventStream).publish(EventURN.PRODUCT_PUBLISHED.urn, ProductEventPayload(100))
    }

    @Test
    fun noTitle() {
        // WHEN
        val ex = assertThrows<HttpClientErrorException> {
            rest.postForEntity(url(110), null, Any::class.java)
        }

        // THEN
        assertEquals(409, ex.rawStatusCode)
        assertPublishError(110, PublishError.MISSING_TITLE, ex)

        verify(eventStream, never()).publish(any(), any())
    }

    @Test
    fun missingNumericFile() {
        // WHEN
        val ex = assertThrows<HttpClientErrorException> {
            rest.postForEntity(url(111), null, Any::class.java)
        }

        // THEN
        assertEquals(409, ex.rawStatusCode)
        assertPublishError(111, PublishError.MISSING_NUMERIC_FILE, ex)

        verify(eventStream, never()).publish(any(), any())
    }

    @Test
    fun noPicture() {
        // WHEN
        val ex = assertThrows<HttpClientErrorException> {
            rest.postForEntity(url(112), null, Any::class.java)
        }

        // THEN
        assertEquals(409, ex.rawStatusCode)
        assertPublishError(112, PublishError.MISSING_PICTURE, ex)

        verify(eventStream, never()).publish(any(), any())
    }

    private fun assertPublishError(id: Long, err: PublishError, ex: HttpClientErrorException) {
        val product = dao.findById(id).get()
        assertEquals(ProductStatus.DRAFT, product.status)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PUBLISH_ERROR.urn, response.error.code)
        assertNotNull((response.error.data?.get("publishing-errors") as List<String>).find { it == err.name })
    }

    private fun url(id: Long): String =
        "http://localhost:$port/v1/products/$id/publish"
}
