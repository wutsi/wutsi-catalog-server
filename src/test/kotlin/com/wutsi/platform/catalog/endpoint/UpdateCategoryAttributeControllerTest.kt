package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.UpdateCategoryAttributeRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/UpdateCategoryAttributeController.sql"])
public class UpdateCategoryAttributeControllerTest : AbstractSecuredController() {
    companion object {
        const val CATEGORY_ID = 100L
    }

    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: CategoryRepository

    @Test
    fun title() {
        val request = UpdateCategoryAttributeRequest("THIS IS THE VALUE")
        val response = rest.postForEntity(url("title"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val category = dao.findById(CATEGORY_ID).get()
        assertEquals(request.value, category.title)
    }

    @Test
    fun visible() {
        val request = UpdateCategoryAttributeRequest("false")
        val response = rest.postForEntity(url("visible"), request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val category = dao.findById(CATEGORY_ID).get()
        assertFalse(category.visible)
    }

    private fun url(name: String, categoryId: Long = CATEGORY_ID) =
        "http://localhost:$port/v1/categories/$categoryId/attributes/$name"
}
