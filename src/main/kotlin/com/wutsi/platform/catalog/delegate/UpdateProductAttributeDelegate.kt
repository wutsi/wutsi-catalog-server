package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.dto.UpdateProductAttributeRequest
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class UpdateProductAttributeDelegate(
    dao: ProductRepository,
    securityManager: SecurityManager,
) : AbstractDelegate(dao, securityManager) {
    fun invoke(
        id: Long,
        name: String,
        request: UpdateProductAttributeRequest
    ) {
        val product = getProduct(id)

        when (name.lowercase()) {
            "title" -> product.title = request.value ?: "NO TITLE"
            "summary" -> product.summary = request.value
            "description" -> product.description = request.value
            "category-id" -> product.categoryId = request.value?.toLong()
            "visible" -> product.visible = request.value?.toBoolean() ?: false
            "price" -> product.price = if (request.value.isNullOrEmpty()) null else request.value.toDouble()
            "comparable-price" -> product.comparablePrice = request.value?.toDouble()
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
        dao.save(product)
    }
}
