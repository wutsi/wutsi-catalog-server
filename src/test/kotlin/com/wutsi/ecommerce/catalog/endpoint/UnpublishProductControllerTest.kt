package com.wutsi.ecommerce.catalog.endpoint

import com.nhaarman.mockitokotlin2.verify
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.event.EventURN
import com.wutsi.ecommerce.catalog.event.ProductEventPayload
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/UnpublishProductController.sql"])
class UnpublishProductControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: ProductRepository

    @Test
    fun unpublish() {
        // WHEN
        rest.delete(url(100))

        val product = dao.findById(100).get()
        assertEquals(ProductStatus.DRAFT, product.status)

        verify(eventStream).publish(EventURN.PRODUCT_UNPUBLISHED.urn, ProductEventPayload(100))
    }

    private fun url(id: Long): String =
        "http://localhost:$port/v1/products/$id/publish"
}
