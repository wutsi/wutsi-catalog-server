package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.event.EventURN
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class DeleteProductDelegate : AbstractProductDelegate() {
    @Transactional
    fun invoke(id: Long) {
        // Delete
        val product = getProduct(id)
        if (product.isDeleted)
            return

        product.isDeleted = true
        product.deleted = OffsetDateTime.now()
        dao.save(product)

        publish(EventURN.PRODUCT_DELETED, product)
    }
}
