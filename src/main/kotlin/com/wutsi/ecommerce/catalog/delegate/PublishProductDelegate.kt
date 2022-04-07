package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.event.EventURN
import com.wutsi.ecommerce.catalog.event.ProductEventPayload
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
class PublishProductDelegate : AbstractProductDelegate() {
    @Transactional
    fun invoke(id: Long) {
        // Publish
        val product = getProduct(id, true)
        checkPublish(product)
        product.status = ProductStatus.PUBLISHED
        product.published = OffsetDateTime.now()
        dao.save(product)

        // Notify
        eventStream.publish(EventURN.PRODUCT_PUBLISHED.urn, ProductEventPayload(id = id))
    }
}
