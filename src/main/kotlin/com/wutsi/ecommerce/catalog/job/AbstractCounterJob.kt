package com.wutsi.ecommerce.catalog.job

import com.wutsi.platform.core.cron.AbstractCronJob
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

abstract class AbstractCounterJob : AbstractCronJob() {
    @Autowired
    private lateinit var em: EntityManager

    @Autowired
    private lateinit var logger: KVLogger

    override fun getToken(): String? = null

    protected fun exec(sql: String): Long {
        try {
            return em.createNativeQuery(sql).executeUpdate().toLong()
        } catch (ex: Exception) {
            logger.add("sql", sql)
            logger.setException(ex)
            return 0
        }
    }
}
