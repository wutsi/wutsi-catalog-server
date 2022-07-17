package com.wutsi.ecommerce.catalog.endpoint

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.ecommerce.catalog.dto.SearchProductRequest
import com.wutsi.ecommerce.catalog.dto.SearchProductResponse
import com.wutsi.ecommerce.catalog.entity.ProductSort
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.platform.tenant.dto.GetTenantResponse
import com.wutsi.platform.tenant.dto.Tenant
import com.wutsi.platform.tenant.dto.Toggle
import com.wutsi.platform.tenant.entity.ToggleName
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/SearchProductsController.sql"])
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
    fun `search account products - digital product enabled`() {
        // GIVEN
        val tenant = Tenant(
            id = 1,
            name = "test",
            toggles = listOf(
                Toggle(
                    name = ToggleName.STORE_DIGITAL_PRODUCT.name
                )
            )
        )
        doReturn(GetTenantResponse(tenant)).whenever(tenantApi).getTenant(any())

        // WHEN
        val request = SearchProductRequest(
            accountId = 1
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(3, products.size)
        assertTrue(products.map { it.id }.containsAll(listOf(100, 101, 110)))
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
            sortBy = ProductSort.VIEWS.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(6, products.size)
        assertEquals(listOf(101L, 200L, 102L, 100L, 201L, 300L), products.map { it.id })
    }

    @Test
    fun `order by recommended`() {
        // WHEN
        val request = SearchProductRequest(
            sortBy = ProductSort.RECOMMENDED.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(6, products.size)
        assertEquals(listOf(200L, 101L, 102L, 100L, 201L, 300L), products.map { it.id })
    }

    @Test
    fun `order by title`() {
        // WHEN
        val request = SearchProductRequest(
            sortBy = ProductSort.TITLE.name
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(6, products.size)
        assertEquals(listOf(101L, 102L, 100L, 200L, 201L, 300L), products.map { it.id })
    }

    @Test
    fun `never return products from disabled merchant`() {
        // WHEN
        val request = SearchProductRequest(
            accountId = 5
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(0, products.size)
    }

    @Test
    fun `empty status`() {
        // WHEN
        val request = SearchProductRequest(
            status = "",
            limit = 1
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(1, products.size)
    }

    @Test
    fun `digital products not supported`() {
        // WHEN
        val request = SearchProductRequest(
            status = "",
            limit = 1
        )
        val response = rest.postForEntity(url, request, SearchProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val products = response.body!!.products
        assertEquals(1, products.size)
    }
}
