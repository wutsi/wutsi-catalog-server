package com.wutsi.ecommerce.catalog.service.importer

import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.entity.MetricType
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
internal class MetricImporterDailyTest {
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
    private lateinit var service: MetricImporterDaily

    @Autowired
    private lateinit var dao: ProductRepository

    private val date = LocalDate.of(2020, 4, 14)

    @BeforeEach
    fun setUp() {
        File(storageDirectory).deleteRecursively()
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/MetricImporterDaily.sql"])
    fun view() {
        store(MetricType.VIEW)

        service.import(date, MetricType.VIEW)

        assertTotalViews(100, 1000 + 31)
        assertTotalViews(101, 100 + 11)
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/MetricImporterDaily.sql"])
    fun share() {
        store(MetricType.SHARE)

        service.import(date, MetricType.SHARE)

        assertTotalShares(100, 100 + 31)
        assertTotalShares(101, 10 + 11)
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/MetricImporterDaily.sql"])
    fun chat() {
        store(MetricType.CHAT)

        service.import(date, MetricType.CHAT)

        assertTotalChats(100, 10 + 31)
        assertTotalChats(101, 1 + 11)
    }

    private fun store(type: MetricType) {
        val path = "aggregates/daily/" +
            date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) +
            "/" + type.name.lowercase() + ".csv"
        storage.store(path, ByteArrayInputStream(CSV.trimIndent().toByteArray()), "application/csv")
    }

    @Test
    @Sql(value = ["/db/clean.sql", "/db/MetricImporterDaily.sql"])
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
}
