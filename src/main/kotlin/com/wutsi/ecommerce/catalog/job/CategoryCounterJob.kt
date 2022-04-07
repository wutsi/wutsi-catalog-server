package com.wutsi.ecommerce.catalog.job

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CategoryCounterJob : AbstractCounterJob() {
    @Scheduled(cron = "\${wutsi.application.jobs.category-counter.cron}")
    @Transactional
    fun run() {
        totalCount()
        publishedCount()
    }

    private fun totalCount() {
        val sql1 = """
            UPDATE T_CATEGORY
            SET product_count = TMP.cnt
            FROM (
               SELECT category_fk AS id, count(*) as cnt FROM T_PRODUCT WHERE is_deleted=false GROUP BY category_fk
            ) AS TMP
            WHERE TMP.id=T_CATEGORY.id
        """.trimIndent()
        exec("total-count", sql1)

        val sql2 = """
            UPDATE T_CATEGORY
            SET product_count = TMP.cnt
            FROM (
               SELECT sub_category_fk AS id, count(*) as cnt FROM T_PRODUCT WHERE is_deleted=false GROUP BY sub_category_fk
            ) AS TMP
            WHERE TMP.id=T_CATEGORY.id
        """.trimIndent()
        exec("total-count", sql2)
    }

    private fun publishedCount() {
        val sql1 = """
            UPDATE T_CATEGORY
            SET published_product_count = TMP.cnt
            FROM (
               SELECT category_fk AS id, count(*) as cnt FROM T_PRODUCT WHERE is_deleted=false AND status=1 GROUP BY category_fk
            ) AS TMP
            WHERE TMP.id=T_CATEGORY.id
        """.trimIndent()
        exec("total-count", sql1)

        val sql2 = """
            UPDATE T_CATEGORY
            SET published_product_count = TMP.cnt
            FROM (
               SELECT sub_category_fk AS id, count(*) as cnt FROM T_PRODUCT WHERE is_deleted=false AND status=1 GROUP BY sub_category_fk
            ) AS TMP
            WHERE TMP.id=T_CATEGORY.id
        """.trimIndent()
        exec("total-count", sql2)
    }
}
