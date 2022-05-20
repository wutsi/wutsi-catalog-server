package com.wutsi.ecommerce.catalog.job

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.ConversionImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.MetricImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.ScoreImporterOverall
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class MetricImporterJobOverallTest {
    private lateinit var metricImporter: MetricImporterOverall
    private lateinit var scoreImporter: ScoreImporterOverall
    private lateinit var conversionImporter: ConversionImporterOverall

    private val date = LocalDate.now()

    @BeforeEach
    fun setUp() {
        metricImporter = mock()
        conversionImporter = mock()
        scoreImporter = mock()
        doReturn(100L).whenever(metricImporter).import(any(), any())
    }

    @Test
    fun enabled() {
        val job = MetricImporterOverallJob(metricImporter, scoreImporter, conversionImporter, true)

        job.run()

        verify(metricImporter, times(5)).import(any(), any())
        verify(metricImporter).import(date, MetricType.SHARE)
        verify(metricImporter).import(date, MetricType.CHAT)
        verify(metricImporter).import(date, MetricType.VIEW)
        verify(metricImporter).import(date, MetricType.ORDER)
        verify(metricImporter).import(date, MetricType.SALE)

        verify(conversionImporter).import(date, MetricType.ORDER)
        verify(scoreImporter).import(date, MetricType.VIEW)
    }

    @Test
    fun disabled() {
        val job = MetricImporterOverallJob(metricImporter, scoreImporter, conversionImporter, false)

        job.run()

        verify(metricImporter, never()).import(any(), any())
        verify(conversionImporter, never()).import(any(), any())
        verify(scoreImporter, never()).import(any(), any())
    }
}
