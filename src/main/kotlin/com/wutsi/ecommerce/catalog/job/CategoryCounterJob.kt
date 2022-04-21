package com.wutsi.ecommerce.catalog.job

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CategoryCounterJob : AbstractCounterJob() {
    override fun getJobName() = "category-counter"

    override fun doRun(): Long =
        exec(
            """
                UPDATE T_CATEGORY C
                SET
                    update_counters = false,
                    product_count = (
                       SELECT count(*)
                        FROM T_PRODUCT
                        WHERE
                            is_deleted=false AND
                            (category_fk=C.id OR sub_category_fk=C.id)
                    ),
                    published_product_count = (
                       SELECT count(*)
                        FROM T_PRODUCT
                        WHERE
                            status=1 AND
                            is_deleted=false AND
                            (category_fk=C.id  OR sub_category_fk=C.id)
                    )
                WHERE update_counters=true
            """.trimIndent()
        )

    @Scheduled(cron = "\${wutsi.application.jobs.category-counter.cron}")
    @Transactional
    override fun run() {
        super.run()
    }
}
