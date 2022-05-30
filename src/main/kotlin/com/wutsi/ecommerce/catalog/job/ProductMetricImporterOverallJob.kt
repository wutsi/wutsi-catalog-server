package com.wutsi.ecommerce.catalog.job

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductConversionImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductMetricImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.product.ScoreImporterOverall
import com.wutsi.platform.core.cron.AbstractCronJob
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ProductMetricImporterOverallJob(
    private val importer: ProductMetricImporterOverall,
    private val score: ScoreImporterOverall,
    private val conversion: ProductConversionImporterOverall,
    @Value("\${wutsi.application.jobs.product-metric-importer-overall.enabled}") private val enabled: Boolean
) : AbstractCronJob() {
    override fun getJobName(): String = "product-metric-importer-overall"

    override fun getToken(): String? = null

    override fun doRun(): Long {
        val date = LocalDate.now()
        return importer.import(date, MetricType.CHAT) +
            importer.import(date, MetricType.SHARE) +
            importer.import(date, MetricType.VIEW) +
            importer.import(date, MetricType.ORDER) +
            importer.import(date, MetricType.SALE) +
            conversion.import(date, MetricType.ORDER) + // IMPORTANT: MUST BE AFTER ALL METRICS
            score.import(date, MetricType.VIEW) // IMPORTANT: MUST BE THE LAST
    }

    @Scheduled(cron = "\${wutsi.application.jobs.product-metric-importer-overall.cron}")
    override fun run() {
        if (enabled)
            super.run()
    }
}
