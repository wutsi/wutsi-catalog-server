package com.wutsi.ecommerce.catalog.job

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SectionCounterJob : AbstractCounterJob() {
    override fun getJobName() = "section-counter"

    override fun doRun(): Long =
        exec(
            """
                UPDATE T_SECTION S
                SET
                    update_counters = false,
                    product_count = (
                       SELECT count(*)
                        FROM T_SECTION_PRODUCT SP
                            JOIN T_PRODUCT P ON P.id=SP.product_fk
                        WHERE
                            S.id=SP.section_fk AND
                            P.is_deleted=false
                    ),
                    published_product_count = (
                       SELECT count(*)
                        FROM T_SECTION_PRODUCT SP
                            JOIN T_PRODUCT P ON P.id=SP.product_fk
                        WHERE
                            S.id=SP.section_fk AND
                            P.status=1 AND
                            P.is_deleted=false
                    )
                WHERE
                    is_deleted=false AND
                    update_counters=true
            """.trimIndent()
        )

    @Transactional
    @Scheduled(cron = "\${wutsi.application.jobs.section-counter.cron}")
    override fun run() {
        super.run()
    }
}
