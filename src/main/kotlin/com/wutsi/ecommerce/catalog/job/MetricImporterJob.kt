package com.wutsi.ecommerce.catalog.job

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.importer.ConversionImporterDaily
import com.wutsi.ecommerce.catalog.service.importer.MetricImporterDaily
import com.wutsi.ecommerce.catalog.service.importer.ScoreImporterDaily
import com.wutsi.platform.core.cron.AbstractCronJob
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate

@Service
class MetricImporterJob(
    private val importer: MetricImporterDaily,
    private val score: ScoreImporterDaily,
    private val conversion: ConversionImporterDaily,
    private val clock: Clock,
    private val logger: KVLogger
) : AbstractCronJob() {
    override fun getJobName(): String = "metric-importer"

    override fun getToken(): String? = null

    override fun doRun(): Long {
        val date = LocalDate.now(clock).minusDays(1) // Yesterday
        logger.add("date", date)

        return importer.import(date, MetricType.CHAT) +
            importer.import(date, MetricType.SHARE) +
            importer.import(date, MetricType.VIEW) +
            importer.import(date, MetricType.ORDER) +
            conversion.import(date, MetricType.ORDER) + // IMPORTANT: MUST BE AFTER ALL METRICS
            score.import(date, MetricType.VIEW) // IMPORTANT: MUST BE THE LAST
    }

    @Scheduled(cron = "\${wutsi.application.jobs.metric-importer.cron}")
    override fun run() {
        super.run()
    }
}
