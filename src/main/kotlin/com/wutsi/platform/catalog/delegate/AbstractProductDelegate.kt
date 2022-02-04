package com.wutsi.platform.catalog.delegate

import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.entity.ProductEntity
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class AbstractProductDelegate(
    protected val dao: ProductRepository,
    protected val securityManager: SecurityManager,
) {
    fun getProduct(id: Long): ProductEntity {
        val product = dao.findById(id)
            .orElseThrow {
                notFound(id)
            }

        if (product.isDeleted)
            throw notFound(id)

        securityManager.checkOwnership(product)

        return product
    }

    private fun notFound(id: Long) = NotFoundException(
        error = Error(
            code = ErrorURN.PRODUCT_NOT_FOUND.urn,
            parameter = Parameter(
                name = "id",
                type = ParameterType.PARAMETER_TYPE_PATH,
                value = id
            )
        )
    )
}
