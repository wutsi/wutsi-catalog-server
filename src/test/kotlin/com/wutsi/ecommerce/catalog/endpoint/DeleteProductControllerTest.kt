package com.wutsi.ecommerce.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.ecommerce.catalog.dao.CategoryRepository
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpStatusCodeException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/DeleteProductController.sql"])
class DeleteProductControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: ProductRepository

    @Autowired
    private lateinit var categoryDao: CategoryRepository

    @Autowired
    private lateinit var sectionDao: SectionRepository

    @Test
    fun delete() {
        rest.delete(url(100))

        val product = dao.findById(100).get()
        assertTrue(product.isDeleted)
        assertNotNull(product.deleted)

        assertTrue(categoryDao.findById(100).get().updateCounters)
        assertTrue(categoryDao.findById(101).get().updateCounters)

        assertTrue(sectionDao.findById(100).get().updateCounters)
        assertTrue(sectionDao.findById(200).get().updateCounters)
    }

    @Test
    fun notFound() {
        val ex = assertThrows<HttpStatusCodeException> {
            rest.delete(url(9999))
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PRODUCT_NOT_FOUND.urn, response.error.code)
    }

    @Test
    fun deleted() {
        val ex = assertThrows<HttpStatusCodeException> {
            rest.delete(url(900))
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PRODUCT_NOT_FOUND.urn, response.error.code)
    }

    private fun url(productId: Long) = "http://localhost:$port/v1/products/$productId"
}
