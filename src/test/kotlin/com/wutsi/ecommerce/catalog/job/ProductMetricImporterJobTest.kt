package com.wutsi.ecommerce.catalog.job

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductMetricImporter
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductScoreImporter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql"])
internal class ProductMetricImporterJobTest {
    @MockBean
    private lateinit var productMetricImporter: ProductMetricImporter

    @MockBean
    private lateinit var scoreImporter: ProductScoreImporter

    @MockBean
    private lateinit var clock: Clock

    @Autowired
    private lateinit var job: ProductMetricImporterJob

    private val date = LocalDate.of(2020, 4, 14)

    @BeforeEach
    fun setUp() {
        val now = Instant.ofEpochMilli(1586952657000)
        doReturn(now).whenever(clock).instant()
        doReturn(ZoneId.of("UTC")).whenever(clock).zone

        doReturn(100L).whenever(productMetricImporter).import(any(), any())
    }

    @Test
    fun run() {
        job.run()

        verify(productMetricImporter, times(5)).import(any(), any())
        verify(productMetricImporter).import(date, MetricType.SHARE)
        verify(productMetricImporter).import(date, MetricType.CHAT)
        verify(productMetricImporter).import(date, MetricType.VIEW)
        verify(productMetricImporter).import(date, MetricType.ORDER)
        verify(productMetricImporter).import(date, MetricType.SALE)

        verify(scoreImporter).import(date, MetricType.VIEW)
    }
}
