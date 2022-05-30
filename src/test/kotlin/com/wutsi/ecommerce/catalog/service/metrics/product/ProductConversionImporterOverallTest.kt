package com.wutsi.ecommerce.catalog.service.metrics.product

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.platform.core.storage.StorageService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/ProductConversionImporter.sql"])
internal class ProductConversionImporterOverallTest {
    companion object {
        const val CSV: String = """
            "time","tenantid","merchantid","productid","value"
            "1586837219285","1","555","100","31"
            "1586837219485","1","666","101","11"
        """
    }

    @Value("\${wutsi.platform.storage.local.directory:\${user.home}/wutsi/storage}")
    protected lateinit var storageDirectory: String

    @Autowired
    private lateinit var storage: StorageService

    @Autowired
    private lateinit var service: ProductConversionImporterOverall

    @Autowired
    private lateinit var dao: ProductRepository

    private val date = LocalDate.of(2020, 4, 14)

    @BeforeEach
    fun setUp() {
        File(storageDirectory).deleteRecursively()
    }

    @Test
    fun cvr() {
        store()

        service.import(date, MetricType.ORDER)

        assertConversion(100, 0.01)
        assertConversion(101, 0.11)
        assertConversion(102, 0.0)
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/ProductConversionImporter.sql"])
    fun fileNotFound() {
        service.import(date, MetricType.ORDER)

        assertConversion(100, 0.0)
        assertConversion(101, 0.0)
        assertConversion(102, 0.0)
    }

    private fun assertConversion(productId: Long, expected: Double) {
        val product = dao.findById(productId)

        assertEquals(expected, product.get().conversion)
    }

    private fun store() {
        val path = "aggregates/overall/order.csv"
        storage.store(
            path,
            ByteArrayInputStream(CSV.trimIndent().toByteArray()),
            "application/csv"
        )
    }
}
