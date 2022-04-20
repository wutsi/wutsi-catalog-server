package com.wutsi.ecommerce.catalog.job

import com.wutsi.ecommerce.catalog.entity.MetricType
import com.wutsi.ecommerce.catalog.service.MetricImporterOverall
import com.wutsi.platform.core.cron.AbstractCronJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MetricImporterJob(private val overall: MetricImporterOverall) : AbstractCronJob() {
    override fun getJobName(): String = "metric-importer"

    override fun getToken(): String? = null

    override fun doRun(): Long =
        import(MetricType.CHAT) +
            import(MetricType.SHARE) +
            import(MetricType.VIEW)

    @Scheduled(cron = "\${wutsi.application.jobs.metric-importer.cron}")
    override fun run() {
        super.run()
    }

    private fun import(type: MetricType) =
        overall.import(type)
}
