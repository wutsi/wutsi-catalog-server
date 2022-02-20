package com.wutsi.ecommerce.catalog.delegate

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class DeleteProductDelegate : AbstractProductDelegate() {
    @Transactional
    fun invoke(id: Long) {
        val product = getProduct(id)
        product.isDeleted = true
        product.deleted = OffsetDateTime.now()
        dao.save(product)
    }
}
