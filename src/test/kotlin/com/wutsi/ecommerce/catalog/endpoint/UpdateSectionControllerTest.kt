package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.dto.UpdateSectionRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/UpdateSectionController.sql"])
class UpdateSectionControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: SectionRepository

    @Test
    fun update() {
        val url = "http://localhost:$port/v1/sections/100"
        val request = UpdateSectionRequest(title = "THIS IS THE VALUE", sortOrder = 1111)
        val response = rest.postForEntity(url, request, Any::class.java)

        assertEquals(200, response.statusCodeValue)

        val section = dao.findById(100).get()
        assertEquals(request.title, section.title)
        assertEquals(request.sortOrder, section.sortOrder)
    }
}
