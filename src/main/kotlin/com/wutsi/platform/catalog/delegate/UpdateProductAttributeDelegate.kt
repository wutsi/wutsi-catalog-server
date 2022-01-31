package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.PictureRepository
import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.dto.UpdateProductAttributeRequest
import com.wutsi.platform.catalog.entity.PictureEntity
import com.wutsi.platform.catalog.entity.ProductEntity
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
    private val pictureDao: PictureRepository,
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
            "category-id" -> product.categoryId = toLong(request.value)
            "visible" -> product.visible = request.value?.toBoolean() ?: false
            "price" -> product.price = if (request.value.isNullOrEmpty()) null else request.value.toDouble()
            "comparable-price" -> product.comparablePrice = request.value?.toDouble()
            "thumbnail-id" -> product.thumbnail = toPicture(product, request.value)
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

    private fun toLong(value: String?): Long? =
        if (value.isNullOrEmpty())
            null
        else
            value.toLong()

    private fun toPicture(product: ProductEntity, value: String?): PictureEntity? {
        val pictureId = toLong(value)
            ?: return null

        val picture = pictureDao.findById(pictureId)
            .orElseThrow {
                BadRequestException(
                    error = Error(
                        code = ErrorURN.PICTURE_NOT_FOUND.urn,
                        parameter = Parameter(
                            name = "value",
                            value = value,
                            type = ParameterType.PARAMETER_TYPE_PAYLOAD
                        )
                    )
                )
            }

        if (picture.product.id != product.id)
            throw BadRequestException(
                error = Error(
                    code = ErrorURN.PICTURE_NOT_FOUND.urn,
                    parameter = Parameter(
                        name = "value",
                        value = value,
                        type = ParameterType.PARAMETER_TYPE_PAYLOAD
                    )
                )
            )

        return picture
    }
}
