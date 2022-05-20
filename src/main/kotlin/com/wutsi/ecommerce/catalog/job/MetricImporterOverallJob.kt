package com.wutsi.ecommerce.catalog.job

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.ConversionImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.MetricImporterOverall
import com.wutsi.ecommerce.catalog.service.metrics.ScoreImporterOverall
import com.wutsi.platform.core.cron.AbstractCronJob
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * BE CAREFULL: This job can be very resource intensive since it will update the whole database!!!
 */
@Service
class MetricImporterOverallJob(
    private val importer: MetricImporterOverall,
    private val score: ScoreImporterOverall,
    private val conversion: ConversionImporterOverall,
    @Value("\${wutsi.application.jobs.metric-importer-overall.enabled}") private val enabled: Boolean
) : AbstractCronJob() {
    override fun getJobName(): String = "metric-importer-overall"

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

    @Scheduled(cron = "\${wutsi.application.jobs.metric-importer-overall.cron}")
    override fun run() {
        if (enabled)
            super.run()
    }
}
