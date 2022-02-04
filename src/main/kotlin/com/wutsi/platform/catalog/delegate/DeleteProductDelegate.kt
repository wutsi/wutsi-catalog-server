package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class DeleteProductDelegate(
    dao: ProductRepository,
    securityManager: SecurityManager,
) : AbstractProductDelegate(dao, securityManager) {
    @Transactional
    fun invoke(id: Long) {
        val product = getProduct(id)
        product.isDeleted = true
        product.deleted = OffsetDateTime.now()
        dao.save(product)
    }
}
