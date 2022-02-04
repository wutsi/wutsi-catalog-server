package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.dto.AddCategoryRequest
import com.wutsi.platform.catalog.dto.GetProductResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/AddCategoryController.sql"])
public class AddCategoryControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Test
    public fun add() {
        // WHEN
        val url = "http://localhost:$port/v1/products/100/categories"
        val request = AddCategoryRequest(
            categoryId = 100
        )
        rest.postForEntity(url, request, Any::class.java)
        val response = rest.getForEntity("http://localhost:$port/v1/products/100", GetProductResponse::class.java)

        // THEN
        val categories = response.body!!.product.categories
        assertEquals(1, categories.size)
        assertEquals("Yo", categories[0].title)
    }
}
