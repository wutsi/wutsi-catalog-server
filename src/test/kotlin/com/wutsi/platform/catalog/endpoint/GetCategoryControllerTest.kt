package com.wutsi.platform.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.GetCategoryResponse
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/GetCategoryController.sql"])
public class GetCategoryControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var dao: CategoryRepository

    @Test
    public fun get() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/100"
        val response = rest.getForEntity(url, GetCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val category = response.body!!.category
        assertEquals("Yo", category.title)
    }

    @Test
    public fun illegalCategory() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/200"
        val ex = assertThrows<HttpClientErrorException> {
            rest.getForEntity(url, GetCategoryResponse::class.java)
        }

        // THEN
        assertEquals(403, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.ILLEGAL_CATEGORY_ACCESS.urn, response.error.code)
    }

    @Test
    public fun notFound() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/999999"
        val ex = assertThrows<HttpClientErrorException> {
            rest.getForEntity(url, GetCategoryResponse::class.java)
        }

        // THEN
        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.CATEGORY_NOT_FOUND.urn, response.error.code)
    }

    @Test
    public fun deleted() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/900"
        val ex = assertThrows<HttpClientErrorException> {
            rest.getForEntity(url, GetCategoryResponse::class.java)
        }

        // THEN
        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.CATEGORY_NOT_FOUND.urn, response.error.code)
    }
}
