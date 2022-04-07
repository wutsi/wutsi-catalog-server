package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.dto.CreateProductRequest
import com.wutsi.ecommerce.catalog.dto.CreateProductResponse
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.entity.ProductType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/CreateProductController.sql"])
class CreateProductControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: ProductRepository

    private lateinit var url: String

    @BeforeEach
    override fun setUp() {
        super.setUp()

        url = "http://localhost:$port/v1/products"
    }

    @Test
    fun invoke() {
        // WHEN
        val request = CreateProductRequest(
            title = "Test",
            summary = "This is the summary",
            description = "This is the long description of the product",
            price = 15000.0,
            comparablePrice = 20000.0,
            categoryId = 100L,
            subCategoryId = 101L,
            type = ProductType.PHYSICAL.name,
            quantity = 30,
            maxOrder = 5
        )
        val response = rest.postForEntity(url, request, CreateProductResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val product = dao.findById(response.body!!.id).get()
        assertEquals(USER_ID, product.accountId)
        assertEquals(TENANT_ID, product.tenantId)
        assertEquals(request.title, product.title)
        assertEquals(request.summary, product.summary)
        assertEquals(request.description, product.description)
        assertEquals(request.price, product.price)
        assertEquals(request.comparablePrice, product.comparablePrice)
        assertEquals("XAF", product.currency)
        assertEquals(request.categoryId, product.category.id)
        assertEquals(request.subCategoryId, product.subCategory.id)
        assertEquals(request.quantity, product.quantity)
        assertEquals(request.maxOrder, product.maxOrder)
        assertEquals(request.type, product.type.name)
        assertTrue(product.visible)
        assertFalse(product.isDeleted)
        assertNull(product.deleted)
        assertNull(product.published)
        assertEquals(ProductStatus.DRAFT, product.status)
    }
}
