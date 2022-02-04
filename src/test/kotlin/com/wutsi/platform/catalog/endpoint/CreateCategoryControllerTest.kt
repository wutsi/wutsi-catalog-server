package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.CreateCategoryRequest
import com.wutsi.platform.catalog.dto.CreateCategoryResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateCategoryControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var dao: CategoryRepository

    private lateinit var url: String

    @BeforeEach
    override fun setUp() {
        super.setUp()

        url = "http://localhost:$port/v1/categories"
    }

    @Test
    public fun invoke() {
        // WHEN
        val request = CreateCategoryRequest(
            title = "Test",
        )
        val response = rest.postForEntity(url, request, CreateCategoryResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val category = dao.findById(response.body!!.id).get()
        assertEquals(USER_ID, category.accountId)
        assertEquals(TENANT_ID, category.tenantId)
        assertEquals(request.title, category.title)
        assertFalse(category.isDeleted)
        assertNull(category.deleted)
    }
}
