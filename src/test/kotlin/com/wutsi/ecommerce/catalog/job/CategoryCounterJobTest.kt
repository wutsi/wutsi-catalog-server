package com.wutsi.ecommerce.catalog.job

import com.wutsi.ecommerce.catalog.dao.CategoryRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/CategoryCounterJob.sql"])
internal class CategoryCounterJobTest {
    @Autowired
    private lateinit var dao: CategoryRepository

    @Autowired
    private lateinit var job: CategoryCounterJob

    @Test
    fun run() {
        job.run()

        assertCount(100, 3, 2)
        assertCount(101, 3, 2)
        assertCount(102, 0, 0)
        assertCount(200, 1, 1)
        assertCount(201, 555, 55)
    }

    private fun assertCount(id: Long, productCount: Int, publishedProductCount: Int, updateCounters: Boolean = false) {
        val category = dao.findById(id).get()
        assertEquals(productCount, category.productCount, "$id-product-count")
        assertEquals(publishedProductCount, category.publishedProductCount, "$id-published-product-count")
        assertEquals(updateCounters, category.updateCounters, "$id-update-counters")
    }
}
