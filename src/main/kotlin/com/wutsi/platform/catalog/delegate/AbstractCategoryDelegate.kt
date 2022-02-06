package com.wutsi.platform.catalog.delegate

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class AbstractCategoryDelegate(
    protected val dao: CategoryRepository,
    protected val securityManager: SecurityManager,
) {
    fun getCategory(id: Long, checkOwnership: Boolean = true): CategoryEntity {
        val category = dao.findById(id)
            .orElseThrow {
                notFound(id)
            }

        if (category.isDeleted)
            throw notFound(id)

        if (checkOwnership)
            securityManager.checkOwnership(category)

        return category
    }

    private fun notFound(id: Long) = NotFoundException(
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
