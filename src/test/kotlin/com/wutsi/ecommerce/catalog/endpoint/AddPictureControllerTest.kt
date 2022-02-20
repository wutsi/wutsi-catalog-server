package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dao.PictureRepository
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.dto.AddPictureRequest
import com.wutsi.ecommerce.catalog.dto.AddPictureResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/AddPictureController.sql"])
public class AddPictureControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var dao: PictureRepository

    @Autowired
    private lateinit var productDao: ProductRepository

    @Test
    public fun add() {
        // WHEN
        val request = AddPictureRequest(
            url = "https://img.com/1111.png"
        )
        val response = rest.postForEntity(url(100), request, AddPictureResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val id = response.body!!.id
        val picture = dao.findById(id).get()
        assertEquals(100L, picture.product.id)
        assertEquals(request.url, picture.url)

        val product = productDao.findById(100).get()
        assertEquals(id, product.thumbnail?.id)
    }

    @Test
    public fun `do not reset product thumbnail`() {
        // WHEN
        val request = AddPictureRequest(
            url = "https://img.com/2222.png"
        )
        val response = rest.postForEntity(url(200), request, AddPictureResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val id = response.body!!.id
        val product = productDao.findById(200).get()
        assertEquals(200, product.thumbnail?.id)
        assertTrue(product.thumbnail?.id != id)
    }

    private fun url(productId: Long) = "http://localhost:$port/v1/products/$productId/pictures"
}
