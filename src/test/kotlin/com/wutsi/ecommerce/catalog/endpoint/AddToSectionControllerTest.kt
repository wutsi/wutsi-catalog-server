package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dao.SectionRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddToSectionControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Autowired
    private lateinit var dao: SectionRepository

    @Test
    @Sql(value = ["/db/clean.sql", "/db/AddToSectionController.sql"])
    fun add() {
        val url = "http://localhost:$port/v1/sections/100/products/102"
        rest.postForEntity(url, null, Any::class.java)

        val section = dao.findById(100).get()
        assertEquals(3, section.productCount)
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/AddToSectionController.sql"])
    fun addAgain() {
        val url = "http://localhost:$port/v1/sections/100/products/101"
        rest.postForEntity(url, null, Any::class.java)

        val section = dao.findById(100).get()
        assertEquals(2, section.productCount)
    }
}
