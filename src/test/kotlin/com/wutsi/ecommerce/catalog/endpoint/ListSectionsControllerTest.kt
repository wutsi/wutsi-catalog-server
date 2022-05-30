package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dto.ListSectionResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/ListSectionController.sql"])
class ListSectionsControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Test
    fun list() {
        // WHEN
        val response =
            rest.getForEntity("http://localhost:$port/v1/sections?account-id=$USER_ID", ListSectionResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val sections = response.body!!.sections
        assertEquals(2, sections.size)

        assertEquals("Deals", sections[0].title)
        assertEquals(1, sections[0].sortOrder)

        assertEquals("Electronic", sections[1].title)
        assertEquals(2, sections[1].sortOrder)
    }
}
