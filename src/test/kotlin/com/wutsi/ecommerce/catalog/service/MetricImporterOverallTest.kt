package com.wutsi.ecommerce.catalog.service

import com.wutsi.ecommerce.catalog.dao.MetricRepository
import com.wutsi.ecommerce.catalog.dao.PeriodRepository
import com.wutsi.ecommerce.catalog.entity.MetricType
import com.wutsi.ecommerce.catalog.entity.PeriodType
import com.wutsi.platform.core.storage.StorageService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import java.io.ByteArrayInputStream
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql"])
internal class MetricImporterOverallTest {
    companion object {
        const val CSV: String = """
            "time","tenantid","merchantid","productid","count"
            "1586837219285","1","555","1","31"
            "1586837219485","1","666","2","11"
        """
    }

    @Autowired
    private lateinit var storage: StorageService

    @Autowired
    private lateinit var service: MetricImporterOverall

    @Autowired
    private lateinit var periodDao: PeriodRepository

    @Autowired
    private lateinit var metricDao: MetricRepository

    @Test
    fun run() {
        store()

        service.import(MetricType.VIEW)

        assertMetric(1, 31.0)
        assertMetric(2, 11.0)
    }

    private fun store() {
        val path = "aggregates/overall/view.csv"
        storage.store(path, ByteArrayInputStream(CSV.trimIndent().toByteArray()), "application/csv")
    }

    private fun assertMetric(productId: Long, expected: Double) {
        val period = periodDao.findByTypeAndYearAndMonth(PeriodType.OVERALL, 0, 0)
        val metric = metricDao.findByPeriodAndProductIdAndType(period, productId, MetricType.VIEW)

        assertTrue(metric.isPresent)
        assertEquals(expected, metric.get().value)
    }
}
