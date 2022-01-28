package com.wutsi.platform.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.platform.catalog.dao.PictureRepository
import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpStatusCodeException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/DeletePictureController.sql"])
public class DeletePictureControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: PictureRepository

    @Autowired
    private lateinit var productDao: ProductRepository

    @Test
    fun delete() {
        rest.delete(url(100))

        val picture = dao.findById(100).get()
        assertTrue(picture.isDeleted)
        assertNotNull(picture.deleted)

        // Thumbnail not changed
        val product = productDao.findById(picture.product.id).get()
        assertEquals(102, product.thumbnail?.id)
    }

    @Test
    fun deleteAndChangeThumbnail() {
        rest.delete(url(200))

        val picture = dao.findById(200).get()
        assertTrue(picture.isDeleted)
        assertNotNull(picture.deleted)

        // Thumbnail not changed
        val product = productDao.findById(picture.product.id).get()
        assertEquals(202, product.thumbnail?.id)
    }

    @Test
    fun deleteAndResetThumbnail() {
        rest.delete(url(300))

        val picture = dao.findById(300).get()
        assertTrue(picture.isDeleted)
        assertNotNull(picture.deleted)

        // Thumbnail not changed
        val product = productDao.findById(picture.product.id).get()
        assertNull(product.thumbnail)
    }

    @Test
    fun notFound() {
        val ex = assertThrows<HttpStatusCodeException> {
            rest.delete(url(1999))
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PICTURE_NOT_FOUND.urn, response.error.code)
    }

    @Test
    fun deleted() {
        val ex = assertThrows<HttpStatusCodeException> {
            rest.delete(url(109))
        }

        assertEquals(404, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.PICTURE_NOT_FOUND.urn, response.error.code)
    }

    private fun url(id: Long) = "http://localhost:$port/v1/products/pictures/$id"
}
