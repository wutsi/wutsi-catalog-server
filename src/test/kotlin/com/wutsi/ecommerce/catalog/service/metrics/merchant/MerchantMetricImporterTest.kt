package com.wutsi.ecommerce.catalog.service.metrics.merchant

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.dao.MerchantRepository
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
@Sql(value = ["/db/clean.sql", "/db/MerchantMetricImporter.sql"])
internal class MerchantMetricImporterTest {
    companion object {
        const val CSV: String = """
            "time","tenantid","merchantid","productid","value"
            "1586837219285","1","1","100","31"
            "1586837219485","1","2","101","10"
            "1586837219485","1","2","101","1"
        """
    }

    @Value("\${wutsi.platform.storage.local.directory:\${user.home}/wutsi/storage}")
    protected lateinit var storageDirectory: String

    @Autowired
    private lateinit var storage: StorageService

    @Autowired
    private lateinit var service: MerchantMetricImporter

    @Autowired
    private lateinit var dao: MerchantRepository

    private val date = LocalDate.of(2020, 4, 14)


    @BeforeEach
    fun setUp() {
        File(storageDirectory).deleteRecursively()
    }

    @Test
    fun view() {
        store(MetricType.VIEW)

        val result = service.import(date, MetricType.VIEW)

        assertEquals(2, result)
        assertTotalViews(1, 1100)
        assertTotalViews(2, 100)
        assertTotalViews(3, 1)
    }

    @Test
    fun share() {
        store(MetricType.SHARE)

        val result = service.import(date, MetricType.SHARE)

        assertEquals(2, result)
        assertTotalShares(1, 110)
        assertTotalShares(2, 10)
        assertTotalShares(3, 2)
    }

    @Test
    fun chat() {
        store(MetricType.CHAT)

        val result = service.import(date, MetricType.CHAT)

        assertEquals(2, result)
        assertTotalChats(1, 11)
        assertTotalChats(2, 1)
        assertTotalChats(3, 3)
    }

    @Test
    fun sale() {
        store(MetricType.SALE)

        val result = service.import(date, MetricType.SALE)

        assertEquals(2, result)
        assertTotalSales(1, 65000)
        assertTotalSales(2, 15000)
        assertTotalSales(3, 4)
    }

    @Test
    fun order() {
        store(MetricType.ORDER)

        val result = service.import(date, MetricType.ORDER)

        assertEquals(2, result)
        assertTotalOrders(1, 36)
        assertTotalOrders(2, 5)
        assertTotalOrders(3, 5)
    }

    private fun assertTotalOrders(merchantId: Long, expected: Long) {
        val merchant = dao.findByAccountId(merchantId)

        assertEquals(expected, merchant.get().totalOrders)
    }

    private fun assertTotalShares(merchantId: Long, expected: Long) {
        val merchant = dao.findByAccountId(merchantId)

        assertEquals(expected, merchant.get().totalShares)
    }

    private fun assertTotalViews(merchantId: Long, expected: Long) {
        val merchant = dao.findByAccountId(merchantId)

        assertEquals(expected, merchant.get().totalViews)
    }

    private fun assertTotalChats(merchantId: Long, expected: Long) {
        val merchant = dao.findByAccountId(merchantId)

        assertEquals(expected, merchant.get().totalChats)
    }

    private fun assertTotalSales(merchantId: Long, expected: Long) {
        val merchant = dao.findByAccountId(merchantId)

        assertEquals(expected, merchant.get().totalSales)
    }

    private fun store(type: MetricType) {
        val path = "aggregates/daily/" +
            date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) +
            "/" + type.name.lowercase() + ".csv"
        storage.store(path, ByteArrayInputStream(CSV.trimIndent().toByteArray()), "application/csv")
    }

}
