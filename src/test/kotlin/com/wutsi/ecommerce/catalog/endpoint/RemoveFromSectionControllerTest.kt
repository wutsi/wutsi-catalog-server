package com.wutsi.ecommerce.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.dto.GetSectionResponse
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/RemoveFromSectionController.sql"])
class RemoveFromSectionControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: SectionRepository

    @Test
    @Sql(value = ["/db/clean.sql", "/db/RemoveFromSectionController.sql"])
    fun remove() {
        val url = "http://localhost:$port/v1/sections/100/products/101"
        rest.delete(url)

        val section = dao.findById(100).get()
        assertEquals(2, section.productCount)
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/RemoveFromSectionController.sql"])
    fun removeDeleted() {
        val url = "http://localhost:$port/v1/sections/100/products/109"
        rest.delete(url)

        val section = dao.findById(100).get()
        assertEquals(2, section.productCount)
    }

    @Test
    fun removeInvalidProduct() {
        val url = "http://localhost:$port/v1/sections/100/products/9999"
        val ex = assertThrows<HttpStatusCodeException> {
            rest.delete(url, GetSectionResponse::class.java)
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PRODUCT_NOT_FOUND.urn, response.error.code)
    }
}
