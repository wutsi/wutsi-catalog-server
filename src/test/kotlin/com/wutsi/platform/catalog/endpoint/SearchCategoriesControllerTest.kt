package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.dto.SearchCategoryRequest
import com.wutsi.platform.catalog.dto.SearchCategoryResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/SearchCategoryController.sql"])
public class SearchCategoriesControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Test
    public fun invoke() {
        // WHEN
        val url = "http://localhost:$port/v1/categories/search"
        val request = SearchCategoryRequest(
            accountId = USER_ID
        )
        val response = rest.postForEntity(url, request, SearchCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val categories = response.body!!.categories.sortedBy { it.id }
        assertEquals(2, categories.size)

        assertEquals(100L, categories[0].id)
        assertEquals("Yo", categories[0].title)

        assertEquals(110L, categories[1].id)
        assertEquals("Man", categories[1].title)
    }
}
