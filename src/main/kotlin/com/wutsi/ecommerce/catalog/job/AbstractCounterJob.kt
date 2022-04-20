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

    protected fun exec(step: String, sql: String): Int {
        logger.add("step", step)
        logger.add("sql", sql)
        try {
            val result = em.createNativeQuery(sql).executeUpdate()
            logger.add("result", result)
            return result
        } catch (ex: Exception) {
            logger.setException(ex)
            return 0
        } finally {
            logger.log()
        }
    }
}
