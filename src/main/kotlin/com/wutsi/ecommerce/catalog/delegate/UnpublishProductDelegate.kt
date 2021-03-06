package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.event.EventURN
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UnpublishProductDelegate : AbstractProductDelegate() {
    @Transactional
    fun invoke(id: Long) {
        // Publish
        val product = getProduct(id, true)
        product.status = ProductStatus.DRAFT
        dao.save(product)

        // Notify
        publish(EventURN.PRODUCT_UNPUBLISHED, product)
    }
}
