package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.dto.CreateSectionRequest
import com.wutsi.ecommerce.catalog.dto.CreateSectionResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/CreateSectionController.sql"])
class CreateSectionControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: SectionRepository

    private lateinit var url: String

    @BeforeEach
    override fun setUp() {
        super.setUp()

        url = "http://localhost:$port/v1/sections"
    }

    @Test
    @Sql(value = ["/db/clean.sql"])
    fun createFirstSection() {
        // WHEN
        val request = CreateSectionRequest(
            title = "Test",
        )
        val response = rest.postForEntity(url, request, CreateSectionResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val section = dao.findById(response.body!!.id).get()
        assertEquals(AbstractSecuredController.USER_ID, section.accountId)
        assertEquals(AbstractSecuredController.TENANT_ID, section.tenantId)
        assertEquals(request.title, section.title)
        assertEquals(1, section.sortOrder)
        assertFalse(section.isDeleted)
        assertNull(section.deleted)
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/CreateSectionController.sql"])
    fun createNewSection() {
        // WHEN
        val request = CreateSectionRequest(
            title = "Test",
        )
        val response = rest.postForEntity(url, request, CreateSectionResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val section = dao.findById(response.body!!.id).get()
        assertEquals(AbstractSecuredController.USER_ID, section.accountId)
        assertEquals(AbstractSecuredController.TENANT_ID, section.tenantId)
        assertEquals(request.title, section.title)
        assertEquals(2, section.sortOrder)
        assertFalse(section.isDeleted)
        assertNull(section.deleted)
    }
}
