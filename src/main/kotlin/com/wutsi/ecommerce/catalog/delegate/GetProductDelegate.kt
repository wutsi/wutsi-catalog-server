package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.GetProductResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetProductDelegate : AbstractProductDelegate() {
    @Transactional
    fun invoke(id: Long): GetProductResponse {
        val product = getProduct(id, false)
        return GetProductResponse(
            product = product.toProduct()
        )
    }
}
