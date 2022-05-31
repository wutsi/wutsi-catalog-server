package com.wutsi.ecommerce.catalog.job

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductConversionImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductMetricImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductScoreImporterOverall
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
    properties = [
        "wutsi.application.jobs.product-metric-importer-overall.enabled=true"
    ]
)
internal class ProductMetricImporterJobOverallTest {
    @MockBean
    private lateinit var metricImporter: ProductMetricImporterOverall

    @MockBean
    private lateinit var scoreImporter: ProductScoreImporterOverall

    @MockBean
    private lateinit var conversionImporter: ProductConversionImporterOverall

    @Autowired
    private lateinit var job: ProductMetricImporterOverallJob

    private val date = LocalDate.now()

    @Test
    fun enabled() {
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
}
