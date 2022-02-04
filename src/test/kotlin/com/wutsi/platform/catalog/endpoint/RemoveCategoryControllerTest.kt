package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.dto.GetProductResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/RemoveCategoryController.sql"])
public class RemoveCategoryControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Test
    public fun add() {
        // WHEN
        val url = "http://localhost:$port/v1/products/100/categories/110"
        rest.delete(url)
        val response = rest.getForEntity("http://localhost:$port/v1/products/100", GetProductResponse::class.java)

        // THEN
        val categories = response.body!!.product.categories
        assertEquals(1, categories.size)
        assertEquals("Yo", categories[0].title)
    }
}
