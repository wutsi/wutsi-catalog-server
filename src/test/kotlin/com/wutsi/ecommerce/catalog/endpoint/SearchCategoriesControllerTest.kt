package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.LanguageClientHttpRequestInterceptor
import com.wutsi.ecommerce.catalog.dto.SearchCategoryRequest
import com.wutsi.ecommerce.catalog.dto.SearchCategoryResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/SearchCategoryController.sql"])
public class SearchCategoriesControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Test
    public fun topCategories() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/search"
        val request = SearchCategoryRequest(
            parentId = null
        )
        val response = rest.postForEntity(url, request, SearchCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val categories = response.body!!.categories.sortedBy { it.id }
        assertEquals(2, categories.size)

        assertEquals(100L, categories[0].id)
        assertEquals("Electronic", categories[0].title)
        assertNull(categories[0].parentId)

        assertEquals(110L, categories[1].id)
        assertEquals("Home", categories[1].title)
        assertNull(categories[1].parentId)
    }

    @Test
    public fun subCategories() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/search"
        val request = SearchCategoryRequest(
            parentId = 100L
        )
        val response = rest.postForEntity(url, request, SearchCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val categories = response.body!!.categories.sortedBy { it.id }
        assertEquals(3, categories.size)

        assertEquals(101L, categories[0].id)
        assertEquals("Cell Phone", categories[0].title)
        assertEquals(100L, categories[0].parentId)

        assertEquals(102L, categories[1].id)
        assertEquals("TV", categories[1].title)
        assertEquals(100L, categories[1].parentId)

        assertEquals(103L, categories[2].id)
        assertEquals("Camera", categories[2].title)
        assertEquals(100L, categories[2].parentId)
    }

    @Test
    public fun french() {
        // GIVEN
        rest.interceptors.add(LanguageClientHttpRequestInterceptor("fr"))

        // WHEN
        val url = "http://localhost:$port/v1/categories/search"
        val request = SearchCategoryRequest(
            parentId = null
        )
        val response = rest.postForEntity(url, request, SearchCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val categories = response.body!!.categories.sortedBy { it.id }
        assertEquals(2, categories.size)

        assertEquals("Électronique", categories[0].title)
        assertEquals("Maison", categories[1].title)
    }
}
