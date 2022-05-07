package com.wutsi.ecommerce.catalog.service.facebook

import com.wutsi.ecommerce.catalog.entity.PictureEntity
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.platform.tenant.dto.Tenant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class FacebookMapperTest {
    private val tenant = Tenant(
        numberFormat = "#,###,##0",
        monetaryFormat = "#,###,##0 FCFA",
        currency = "XAF",
        name = "Test",
        webappUrl = "https://wutsi.me"
    )
    private val mapper = FacebookMapper()

    @Test
    fun `map product`() {
        val product = createProduct(price = 1000.0, quantity = 10)

        val result = mapper.toFacebookProduct(product, tenant)

        assertEquals(product.id.toString(), result.id)
        assertEquals(product.title, result.title)
        assertEquals(product.summary, result.description)
        assertEquals("new", result.condition)
        assertEquals("in stock", result.availability)
        assertEquals("Unknown Brand", result.brand)
        assertEquals("1,000 XAF", result.price)
        assertEquals(product.thumbnail?.url, result.imageLink)
        assertEquals("https://wutsi.me/product?id=${product.id}", result.link)
        assertNull(result.salesPrice)
    }

    @Test
    fun `map unavailable product`() {
        val product = createProduct(quantity = 0)

        val result = mapper.toFacebookProduct(product, tenant)

        assertEquals("out of stock", result.availability)
    }

    @Test
    fun `map product in sales`() {
        val product = createProduct(price = 1000.0, comparablePrice = 1500.0)

        val result = mapper.toFacebookProduct(product, tenant)

        assertEquals("1,000 XAF", result.salesPrice)
        assertEquals("1,500 XAF", result.price)
    }

    @Test
    fun `map product with no summary`() {
        val product = createProduct(summary = null)

        val result = mapper.toFacebookProduct(product, tenant)

        assertEquals(product.title, result.description)
    }

    private fun createProduct(
        quantity: Int = 10,
        price: Double = 1000.0,
        comparablePrice: Double? = null,
        summary: String? = "Description of product 111"
    ) = ProductEntity(
        id = 111,
        title = "Product 111",
        summary = summary,
        quantity = quantity,
        price = price,
        comparablePrice = comparablePrice,
        thumbnail = PictureEntity(
            id = 111,
            url = "https://www.img.com/1.png"
        )
    )
}
