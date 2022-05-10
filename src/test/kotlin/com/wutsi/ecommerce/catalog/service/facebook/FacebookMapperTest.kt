package com.wutsi.ecommerce.catalog.service.facebook

import com.wutsi.ecommerce.catalog.entity.PictureEntity
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.platform.account.dto.Account
import com.wutsi.platform.tenant.dto.Tenant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FacebookMapperTest {
    private val tenant = Tenant(
        numberFormat = "#,###,##0",
        monetaryFormat = "#,###,##0 FCFA",
        currency = "XAF",
        name = "Test",
        webappUrl = "https://wutsi.me"
    )

    private val account = Account(
        displayName = "Nike"
    )

    private lateinit var mapper: FacebookMapper

    @BeforeEach
    fun setUp() {
        mapper = FacebookMapper()
    }

    @Test
    fun `map product`() {
        val product = createProduct(price = 1000.0, quantity = 10)

        val result = mapper.toFacebookProduct(product, account, tenant)

        assertEquals(product.id.toString(), result.id)
        assertEquals(product.title, result.title)
        assertEquals(product.summary, result.description)
        assertEquals("new", result.condition)
        assertEquals("in stock", result.availability)
        assertEquals(account.displayName, result.brand)
        assertEquals("1,000 XAF", result.price)
        assertEquals(product.thumbnail?.url, result.imageLink)
        assertNull(result.googleProductCategory)
        assertEquals(
            listOf("https://www.img.com/12.png", "https://www.img.com/13.png", "https://www.img.com/14.png"),
            result.additionalImageLink
        )
        assertEquals("https://wutsi.me/product?id=${product.id}", result.link)
        assertNull(result.salesPrice)
    }

    @Test
    fun `map unavailable product`() {
        val product = createProduct(quantity = 0)

        val result = mapper.toFacebookProduct(product, account, tenant)

        assertEquals("out of stock", result.availability)
    }

    @Test
    fun `map product in sales`() {
        val product = createProduct(price = 1000.0, comparablePrice = 1500.0)

        val result = mapper.toFacebookProduct(product, account, tenant)

        assertEquals("1,000 XAF", result.salesPrice)
        assertEquals("1,500 XAF", result.price)
    }

    @Test
    fun `map product with no summary`() {
        val product = createProduct(summary = null)

        val result = mapper.toFacebookProduct(product, account, tenant)

        assertEquals(product.title, result.description)
    }

    @Test
    fun `map product with no title all caps`() {
        val product = createProduct(title = "HELLO WORLD")

        val result = mapper.toFacebookProduct(product, account, tenant)

        assertEquals("Hello world", result.title)
    }

    private fun createProduct(
        title: String = "Product 111",
        quantity: Int = 10,
        price: Double = 1000.0,
        comparablePrice: Double? = null,
        summary: String? = "Description of product 111"
    ) = ProductEntity(
        id = 111,
        title = title,
        summary = summary,
        quantity = quantity,
        price = price,
        comparablePrice = comparablePrice,
        thumbnail = PictureEntity(id = 11, url = "https://www.img.com/11.png"),
        pictures = listOf(
            PictureEntity(id = 11, url = "https://www.img.com/11.png"),
            PictureEntity(id = 12, url = "https://www.img.com/12.png"),
            PictureEntity(id = 13, url = "https://www.img.com/13.png"),
            PictureEntity(id = 14, url = "https://www.img.com/14.png"),
        )
    )
}
