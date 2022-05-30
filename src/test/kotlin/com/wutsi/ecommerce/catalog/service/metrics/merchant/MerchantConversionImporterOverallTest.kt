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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/MerchantConversionImporter.sql"])
internal class MerchantConversionImporterOverallTest {
    companion object {
        const val CSV: String = """
            "time","tenantid","merchantid","productid","value"
            "1586837219285","1","1","100","31"
            "1586837219485","1","2","101","10"
            "1586837219485","1","3","101","1"
        """
    }

    @Value("\${wutsi.platform.storage.local.directory:\${user.home}/wutsi/storage}")
    protected lateinit var storageDirectory: String

    @Autowired
    private lateinit var storage: StorageService

    @Autowired
    private lateinit var service: MerchantConversionImporterOverall

    @Autowired
    private lateinit var dao: MerchantRepository

    private val date = LocalDate.of(2020, 4, 14)

    @BeforeEach
    fun setUp() {
        File(storageDirectory).deleteRecursively()
    }

    @Test
    fun import() {
        store(MetricType.VIEW)

        val result = service.import(date, MetricType.VIEW)

        assertEquals(3, result)
        assertConversion(1, .01)
        assertConversion(2, 0.11)
        assertConversion(3, 1.0)
    }

    private fun assertConversion(merchantId: Long, expected: Double) {
        val merchant = dao.findByAccountId(merchantId)

        assertEquals(expected, merchant.get().conversion)
    }

    private fun store(type: MetricType) {
        val path = "aggregates/overall/" + type.name.lowercase() + ".csv"
        storage.store(path, ByteArrayInputStream(CSV.trimIndent().toByteArray()), "application/csv")
    }
}
