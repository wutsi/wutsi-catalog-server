package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.dto.SearchProductRequest
import com.wutsi.platform.catalog.dto.SearchProductResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/SearchProductController.sql"])
class SearchProductsControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    private lateinit var url: String

    @BeforeEach
    override fun setUp() {
        super.setUp()

        url = "http://localhost:$port/v1/products/search"
    }

    @Test
    fun `search account products`() {
        // WHEN
        val request = SearchProductRequest(
            accountId = 1
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(2, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100, 101)))
    }

    @Test
    fun `search visible products`() {
        // WHEN
        val request = SearchProductRequest(
            visible = true
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(3, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100, 101, 200)))
    }

    @Test
    fun `filtering`() {
        // WHEN
        val request = SearchProductRequest(
            visible = true,
            categoryIds = listOf(100, 101),
            productIds = listOf(100, 101, 102, 103, 104, 900, 200, 300)
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products.sortedBy { it.id }
        assertEquals(3, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100, 101, 200)))
    }
}
