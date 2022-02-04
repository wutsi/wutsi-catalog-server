package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.UpdateCategoryAttributeRequest
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class UpdateCategoryAttributeDelegate(
    dao: CategoryRepository,
    securityManager: SecurityManager,
) : AbstractCategoryDelegate(dao, securityManager) {
    fun invoke(
        id: Long,
        name: String,
        request: UpdateCategoryAttributeRequest
    ) {
        val category = getCategory(id)

        when (name.lowercase()) {
            "title" -> category.title = toString(request.value) ?: "NO TITLE"
            "visible" -> category.visible = request.value?.toBoolean() ?: false
            else -> throw BadRequestException(
                error = Error(
                    code = ErrorURN.INVALID_ATTRIBUTE.urn,
                    parameter = Parameter(
                        name = "name",
                        value = name,
                        type = ParameterType.PARAMETER_TYPE_PATH
                    )
                )
            )
        }
        dao.save(category)
    }

    private fun toString(value: String?): String? =
        if (value.isNullOrEmpty())
            null
        else
            value
}
