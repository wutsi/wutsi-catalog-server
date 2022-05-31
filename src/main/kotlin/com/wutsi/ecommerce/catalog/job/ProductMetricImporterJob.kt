package com.wutsi.ecommerce.catalog.job

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductConversionImporter
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductMetricImporter
import com.wutsi.ecommerce.catalog.service.metrics.product.ProductScoreImporter
import com.wutsi.platform.core.cron.AbstractCronJob
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate

@Service
class ProductMetricImporterJob(
    private val importer: ProductMetricImporter,
    private val score: ProductScoreImporter,
    private val conversion: ProductConversionImporter,
    private val clock: Clock,
    private val logger: KVLogger
) : AbstractCronJob() {
    override fun getJobName(): String = "product-metric-importer"

    override fun doRun(): Long {
        val date = LocalDate.now(clock).minusDays(1) // Yesterday
        logger.add("date", date)

        return importer.import(date, MetricType.CHAT) +
            importer.import(date, MetricType.SHARE) +
            importer.import(date, MetricType.VIEW) +
            importer.import(date, MetricType.ORDER) +
            importer.import(date, MetricType.SALE) +
            conversion.import(date, MetricType.ORDER) + // IMPORTANT: MUST BE AFTER ALL METRICS
            score.import(date, MetricType.VIEW) // IMPORTANT: MUST BE THE LAST
    }

    @Scheduled(cron = "\${wutsi.application.jobs.product-metric-importer.cron}")
    override fun run() {
        super.run()
    }
}
