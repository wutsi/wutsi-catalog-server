package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.CategoryRepository
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.entity.CategoryEntity
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.ecommerce.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AbstractProductDelegate {
    @Autowired
    protected lateinit var dao: ProductRepository

    @Autowired
    protected lateinit var securityManager: SecurityManager

    @Autowired
    protected lateinit var categoryDao: CategoryRepository

    fun getProduct(id: Long, checkOwnership: Boolean = true): ProductEntity {
        val product = dao.findById(id)
            .orElseThrow {
                notFound(id)
            }

        if (product.isDeleted)
            throw notFound(id)

        if (checkOwnership)
            securityManager.checkOwnership(product)

        securityManager.checkTenant(product)

        return product
    }

    fun getCategory(id: Long): CategoryEntity =
        categoryDao.findById(id)
            .orElseThrow {
                NotFoundException(
                    error = Error(
                        code = ErrorURN.CATEGORY_NOT_FOUND.urn,
                        parameter = Parameter(
                            name = "id",
                            type = ParameterType.PARAMETER_TYPE_PATH,
                            value = id
                        )
                    )
                )
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
