package com.wutsi.platform.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.platform.catalog.LanguageClientHttpRequestInterceptor
import com.wutsi.platform.catalog.dto.GetCategoryResponse
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/GetCategoryController.sql"])
public class GetCategoryControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Test
    public fun topCategory() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/100"
        val response = rest.getForEntity(url, GetCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val category = response.body!!.category
        assertEquals("Electronic", category.title)
        assertNull(category.parentId)
    }

    @Test
    public fun subCategory() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/101"
        val response = rest.getForEntity(url, GetCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val category = response.body!!.category
        assertEquals("Cell Phone", category.title)
        assertEquals(100, category.parentId)
    }

    @Test
    public fun getFrench() {
        // GIVEN
        rest.interceptors.add(LanguageClientHttpRequestInterceptor("fr"))

        // WHEN
        val url = "http://localhost:$port/v1/categories/100"
        val response = rest.getForEntity(url, GetCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val category = response.body!!.category
        assertEquals("Électronique", category.title)
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
}
