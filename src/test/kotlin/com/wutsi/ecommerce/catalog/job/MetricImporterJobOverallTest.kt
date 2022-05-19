package com.wutsi.ecommerce.catalog.job

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.ConversionImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.MetricImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.ScoreImporterOverall
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql"])
internal class MetricImporterJobOverallTest {
    @MockBean
    private lateinit var metricImporter: MetricImporterOverall

    @MockBean
    private lateinit var scoreImporter: ScoreImporterOverall

    @MockBean
    private lateinit var conversionImporter: ConversionImporterOverall

    @Autowired
    private lateinit var job: MetricImporterOverallJob

    private val date = LocalDate.now()

    @BeforeEach
    fun setUp() {
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

        verify(conversionImporter).import(date, MetricType.ORDER)
        verify(scoreImporter).import(date, MetricType.VIEW)
    }
}
