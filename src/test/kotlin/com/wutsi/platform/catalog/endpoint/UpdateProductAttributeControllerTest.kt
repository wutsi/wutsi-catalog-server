package com.wutsi.platform.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.dto.UpdateProductAttributeRequest
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpStatusCodeException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/UpdateProductAttributeController.sql"])
class UpdateProductAttributeControllerTest : AbstractSecuredController() {
    companion object {
        const val PRODUCT_ID = 100L
    }

    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: ProductRepository

    @Test
    fun title() {
        val request = UpdateProductAttributeRequest("THIS IS THE VALUE")
        val response = rest.postForEntity(url("title"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertEquals(request.value, product.title)
    }

    @Test
    fun summary() {
        val request = UpdateProductAttributeRequest("THIS IS THE VALUE")
        val response = rest.postForEntity(url("summary"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertEquals(request.value, product.summary)
    }

    @Test
    fun description() {
        val request = UpdateProductAttributeRequest("THIS IS THE VALUE")
        val response = rest.postForEntity(url("description"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertEquals(request.value, product.description)
    }

    @Test
    fun visible() {
        val request = UpdateProductAttributeRequest("false")
        val response = rest.postForEntity(url("visible"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertFalse(product.visible)
    }

    @Test
    fun categoryId() {
        val request = UpdateProductAttributeRequest("555")
        val response = rest.postForEntity(url("category-id"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertEquals(request.value?.toLong(), product.categoryId)
    }

    @Test
    fun price() {
        val request = UpdateProductAttributeRequest("10000")
        val response = rest.postForEntity(url("price"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertEquals(request.value?.toDouble(), product.price)
    }

    @Test
    fun comparablePrice() {
        val request = UpdateProductAttributeRequest("15000")
        val response = rest.postForEntity(url("comparable-price"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertEquals(request.value?.toDouble(), product.comparablePrice)
    }

    @Test
    fun thumbnailId() {
        val request = UpdateProductAttributeRequest("102")
        val response = rest.postForEntity(url("thumbnail-id"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertEquals(request.value?.toLong(), product.thumbnail?.id)
    }

    @Test
    fun resetThumbnailId() {
        val request = UpdateProductAttributeRequest("")
        val response = rest.postForEntity(url("thumbnail-id"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(PRODUCT_ID).get()
        assertNull(product.thumbnail)
    }

    @Test
    fun illegalAccount() {
        val request = UpdateProductAttributeRequest("15000")
        val ex = assertThrows<HttpStatusCodeException> {
            rest.postForEntity(url("price", 200), request, Any::class.java)
        }

        assertEquals(403, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.ILLEGAL_PRODUCT_ACCESS.urn, response.error.code)
    }

    @Test
    fun notFound() {
        val request = UpdateProductAttributeRequest("15000")
        val ex = assertThrows<HttpStatusCodeException> {
            rest.postForEntity(url("price", 99999), request, Any::class.java)
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PRODUCT_NOT_FOUND.urn, response.error.code)
    }

    @Test
    fun deleted() {
        val request = UpdateProductAttributeRequest("15000")
        val ex = assertThrows<HttpStatusCodeException> {
            rest.postForEntity(url("price", 900), request, Any::class.java)
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PRODUCT_NOT_FOUND.urn, response.error.code)
    }

    private fun url(name: String, productId: Long = PRODUCT_ID) =
        "http://localhost:$port/v1/products/$productId/attributes/$name"
}
