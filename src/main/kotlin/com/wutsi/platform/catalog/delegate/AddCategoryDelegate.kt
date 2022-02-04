package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.dto.AddCategoryRequest
import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
public class AddCategoryDelegate(
    dao: ProductRepository,
    securityManager: SecurityManager,
    private val categoryDao: CategoryRepository
) : AbstractProductDelegate(dao, securityManager) {
    @Transactional
    public fun invoke(id: Long, request: AddCategoryRequest) {
        val product = getProduct(id)
        val category = getCategory(request.categoryId)
        if (product.categories.add(category))
            dao.save(product)
    }

    private fun getCategory(id: Long): CategoryEntity {
        val category = categoryDao.findById(id)
            .orElseThrow {
                notFound(id)
            }

        if (category.isDeleted)
            throw notFound(id)

        securityManager.checkOwnership(category)
        return category
    }

    private fun notFound(id: Long) = NotFoundException(
        error = Error(
            code = ErrorURN.CATEGORY_NOT_FOUND.urn,
            parameter = Parameter(
                name = "id",
                type = ParameterType.PARAMETER_TYPE_PAYLOAD,
                value = id
            )
        )
    )
}
