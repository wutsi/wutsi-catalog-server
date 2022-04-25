package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dto.SearchProductRequest
import com.wutsi.ecommerce.catalog.dto.SearchProductResponse
import com.wutsi.ecommerce.catalog.entity.ProductSort
import com.wutsi.ecommerce.catalog.entity.ProductStatus
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
    fun `search by status`() {
        // WHEN
        val request = SearchProductRequest(
            accountId = 1,
            status = ProductStatus.DRAFT.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(2, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100, 101)))
    }

    @Test
    fun `search by categories`() {
        // WHEN
        val request = SearchProductRequest(
            categoryIds = listOf(100, 201),
            sortBy = ProductSort.RECOMMENDED.name,
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(5, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100, 101, 102, 200, 300)))
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
        assertEquals(5, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100, 101, 102, 200, 201)))
    }

    @Test
    fun `filtering`() {
        // WHEN
        val request = SearchProductRequest(
            visible = true,
            categoryIds = listOf(102, 200),
            productIds = listOf(100, 101, 102, 200, 201, 202, 203)
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products.sortedBy { it.id }
        assertEquals(4, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(101L, 102, 200L, 201L)))
    }

    @Test
    fun `search by section`() {
        // WHEN
        val request = SearchProductRequest(
            visible = true,
            sectionId = 100,
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products.sortedBy { it.id }
        assertEquals(2, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100L, 101L)))
    }

    @Test
    fun `order by price asc`() {
        // WHEN
        val request = SearchProductRequest(
            accountId = 1,
            sortBy = ProductSort.PRICE_ASC.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(2, products.size)
        assertTrue(products[0].price!! < products[1].price!!)
    }

    @Test
    fun `order by price desc`() {
        // WHEN
        val request = SearchProductRequest(
            accountId = 1,
            sortBy = ProductSort.PRICE_DESC.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(2, products.size)
        assertTrue(products[0].price!! > products[1].price!!)
    }

    @Test
    fun `order by views`() {
        // WHEN
        val request = SearchProductRequest(
            visible = true,
            sortBy = ProductSort.VIEWS.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(5, products.size)
        assertEquals(listOf(101L, 200L, 102L, 100L, 201L), products.map { it.id })
    }

    @Test
    fun `order by recommended`() {
        // WHEN
        val request = SearchProductRequest(
            visible = true,
            sortBy = ProductSort.RECOMMENDED.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(5, products.size)
        assertEquals(listOf(200L, 101L, 102L, 100L, 201L), products.map { it.id })
    }
}
