package com.wutsi.ecommerce.catalog.job

import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.endpoint.AbstractSecuredController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/SectionCounterJob.sql"])
internal class SectionCounterJobTest : AbstractSecuredController() {
    @Autowired
    private lateinit var dao: SectionRepository

    @Autowired
    private lateinit var job: SectionCounterJob

    @Test
    fun run() {
        job.run()

        assertCount(100, 3, 2)
        assertCount(200, 666, 66)
        assertCount(300, 0, 0)
        assertCount(900, 100, 10, true)
    }

    private fun assertCount(id: Long, productCount: Int, publishedProductCount: Int, updateCounters: Boolean = false) {
        val section = dao.findById(id).get()
        assertEquals(productCount, section.productCount, "$id-product-count")
        assertEquals(publishedProductCount, section.publishedProductCount, "$id-published-product-count")
        assertEquals(updateCounters, section.updateCounters, "$id-update-counters")
    }
}
