package com.wutsi.ecommerce.catalog.job

import com.wutsi.ecommerce.catalog.entity.MetricType
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
    private val clock: Clock,
    private val logger: KVLogger
) : AbstractCronJob() {
    override fun getJobName(): String = "metric-importer"

    override fun getToken(): String? = null

    override fun doRun(): Long {
        val date = LocalDate.now(clock).minusDays(1) // Yesterday
        logger.add("date", date)

        importer.import(date, MetricType.CHAT)
        importer.import(date, MetricType.SHARE)
        importer.import(date, MetricType.VIEW)
        return score.import(date, MetricType.VIEW)
    }

    @Scheduled(cron = "\${wutsi.application.jobs.metric-importer.cron}")
    override fun run() {
        super.run()
    }
}
