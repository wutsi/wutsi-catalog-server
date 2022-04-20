package com.wutsi.ecommerce.catalog.job

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SectionCounterJob : AbstractCounterJob() {
    override fun getJobName() = "section-counter"

    override fun doRun(): Long {
        totalCount()
        publishedCount()
        return -1
    }

    @Scheduled(cron = "\${wutsi.application.jobs.section-counter.cron}")
    @Transactional
    override fun run() {
        super.run()
    }

    private fun totalCount() {
        val sql = """
            UPDATE T_SECTION
            SET product_count = TMP.cnt
            FROM (
               SELECT section_fk AS id, count(*) AS cnt FROM T_SECTION_PRODUCT S JOIN T_PRODUCT P ON P.id=S.product_fk WHERE P.is_deleted=false GROUP BY section_fk
            ) AS TMP
            WHERE TMP.id=T_SECTION.id;
        """.trimIndent()
        exec("total-count", sql)
    }

    private fun publishedCount() {
        val sql = """
            UPDATE T_SECTION
            SET published_product_count = TMP.cnt
            FROM (
               SELECT section_fk AS id, count(*) AS cnt FROM T_SECTION_PRODUCT S JOIN T_PRODUCT P ON P.id=S.product_fk WHERE P.status=1 AND P.is_deleted=false GROUP BY section_fk
            ) AS TMP
            WHERE TMP.id=T_SECTION.id;
        """.trimIndent()
        exec("published-count", sql)
    }
}
