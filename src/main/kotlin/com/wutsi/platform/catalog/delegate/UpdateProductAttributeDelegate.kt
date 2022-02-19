package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.PictureRepository
import com.wutsi.platform.catalog.dto.UpdateProductAttributeRequest
import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.entity.PictureEntity
import com.wutsi.platform.catalog.entity.ProductEntity
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class UpdateProductAttributeDelegate(private val pictureDao: PictureRepository) : AbstractProductDelegate() {
    fun invoke(
        id: Long,
        name: String,
        request: UpdateProductAttributeRequest
    ) {
        val product = getProduct(id)

        when (name.lowercase()) {
            "title" -> product.title = toString(request.value) ?: "NO TITLE"
            "summary" -> product.summary = toString(request.value)
            "description" -> product.description = toString(request.value)
            "visible" -> product.visible = request.value?.toBoolean() ?: false
            "price" -> product.price = toDouble(request.value)
            "comparable-price" -> product.comparablePrice = toDouble(request.value)
            "thumbnail-id" -> product.thumbnail = toPicture(product, request.value)
            "sub-category-id" -> product.subCategory = toSubCategory(product, request.value)
            "quantity" -> product.quantity = toInt(request.value) ?: 0
            "max-order" -> product.maxOrder = toInt(request.value)
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

    private fun toSubCategory(product: ProductEntity, value: String?): CategoryEntity {
        val id = toLong(value)
            ?: throw BadRequestException(
                error = Error(
                    code = ErrorURN.INVALID_SUB_CATEGORY.urn,
                )
            )

        val category = getCategory(id)
        if (category.parentId != product.category.id)
            throw BadRequestException(
                error = Error(
                    code = ErrorURN.INVALID_SUB_CATEGORY.urn,
                )
            )

        return category
    }

    private fun toString(value: String?): String? =
        if (value.isNullOrEmpty())
            null
        else
            value

    private fun toLong(value: String?): Long? =
        if (value.isNullOrEmpty())
            null
        else
            value.toLong()

    private fun toInt(value: String?): Int? =
        if (value.isNullOrEmpty())
            null
        else
            value.toInt()

    private fun toDouble(value: String?): Double? =
        if (value.isNullOrEmpty())
            null
        else
            value.toDouble()

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
