package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.dto.GetProductResponse
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetProductDelegate(
    dao: ProductRepository,
    securityManager: SecurityManager,
) : AbstractProductDelegate(dao, securityManager) {
    @Transactional
    fun invoke(id: Long): GetProductResponse {
        val product = getProduct(id, false)
        return GetProductResponse(
            product = product.toProduct()
        )
    }
}
