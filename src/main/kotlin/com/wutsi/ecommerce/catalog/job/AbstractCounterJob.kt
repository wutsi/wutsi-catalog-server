package com.wutsi.ecommerce.catalog.job

import com.wutsi.platform.core.logging.DefaultKVLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AbstractCounterJob {
    @Autowired
    private lateinit var em: EntityManager

    protected fun exec(step: String, sql: String) {
        val logger = DefaultKVLogger()
        logger.add("job", "category-counter")
        logger.add("step", step)
        logger.add("sql", sql)
        try {
            val result = em.createNativeQuery(sql).executeUpdate()
            logger.add("result", result)
        } catch (ex: Exception) {
            logger.setException(ex)
        } finally {
            logger.log()
        }
    }
}
