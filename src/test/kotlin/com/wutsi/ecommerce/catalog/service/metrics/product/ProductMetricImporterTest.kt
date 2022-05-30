package com.wutsi.ecommerce.catalog.service.metrics.product

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.platform.core.storage.StorageService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/ProductMetricImporter.sql"])
internal class ProductMetricImporterTest {
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
    private lateinit var service: ProductMetricImporter

    @Autowired
    private lateinit var dao: ProductRepository

    private val date = LocalDate.of(2020, 4, 14)

    @BeforeEach
    fun setUp() {
        File(storageDirectory).deleteRecursively()
    }

    @Test
    fun view() {
        store(MetricType.VIEW)

        service.import(date, MetricType.VIEW)

        assertTotalViews(100, 1000 + 31)
        assertTotalViews(101, 100 + 11)
    }

    @Test
    fun share() {
        store(MetricType.SHARE)

        service.import(date, MetricType.SHARE)

        assertTotalShares(100, 100 + 31)
        assertTotalShares(101, 10 + 11)
    }

    @Test
    fun chat() {
        store(MetricType.CHAT)

        service.import(date, MetricType.CHAT)

        assertTotalChats(100, 10 + 31)
        assertTotalChats(101, 1 + 11)
    }

    @Test
    fun sale() {
        store(MetricType.SALE)

        service.import(date, MetricType.SALE)

        assertTotalSales(100, 50000 + 31)
        assertTotalSales(101, 15000 + 11)
    }

    @Test
    fun fileNotFound() {
        service.import(date, MetricType.VIEW)

        assertTotalViews(100, 1000)
        assertTotalViews(101, 100)
    }

    private fun assertTotalShares(productId: Long, expected: Long) {
        val product = dao.findById(productId)

        assertEquals(expected, product.get().totalShares)
    }

    private fun assertTotalViews(productId: Long, expected: Long) {
        val product = dao.findById(productId)

        assertEquals(expected, product.get().totalViews)
    }

    private fun assertTotalChats(productId: Long, expected: Long) {
        val product = dao.findById(productId)

        assertEquals(expected, product.get().totalChats)
    }

    private fun assertTotalSales(productId: Long, expected: Long) {
        val product = dao.findById(productId)

        assertEquals(expected, product.get().totalSales)
    }

    private fun store(type: MetricType) {
        val path = "aggregates/daily/" +
            date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) +
            "/" + type.name.lowercase() + ".csv"
        storage.store(path, ByteArrayInputStream(CSV.trimIndent().toByteArray()), "application/csv")
    }
}
