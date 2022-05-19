package com.wutsi.ecommerce.catalog.job

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.MetricImporter
import com.wutsi.ecommerce.catalog.service.metrics.ScoreImporter
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
internal class MetricImporterJobTest {
    @MockBean
    private lateinit var metricImporter: MetricImporter

    @MockBean
    private lateinit var scoreImporter: ScoreImporter

    @MockBean
    private lateinit var clock: Clock

    @Autowired
    private lateinit var job: MetricImporterJob

    private val date = LocalDate.of(2020, 4, 14)

    @BeforeEach
    fun setUp() {
        val now = Instant.ofEpochMilli(1586952657000)
        doReturn(now).whenever(clock).instant()
        doReturn(ZoneId.of("UTC")).whenever(clock).zone

        doReturn(100L).whenever(metricImporter).import(any(), any())
    }

    @Test
    fun run() {
        job.run()

        verify(metricImporter, times(4)).import(any(), any())
        verify(metricImporter).import(date, MetricType.SHARE)
        verify(metricImporter).import(date, MetricType.CHAT)
        verify(metricImporter).import(date, MetricType.VIEW)
        verify(metricImporter).import(date, MetricType.ORDER)

        verify(scoreImporter).import(date, MetricType.VIEW)
    }
}
