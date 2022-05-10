package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.PictureRepository
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.ecommerce.catalog.event.EventURN
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class DeletePictureDelegate(
    private val pictureDao: PictureRepository
) : AbstractProductDelegate() {
    @Transactional
    fun invoke(id: Long) {
        val picture = pictureDao.findById(id)
            .orElseThrow {
                NotFoundException(
                    error = Error(
                        code = ErrorURN.PICTURE_NOT_FOUND.urn,
                        parameter = Parameter(
                            name = "id",
                            type = ParameterType.PARAMETER_TYPE_PATH,
                            value = id
                        )
                    )
                )
            }

        if (picture.isDeleted)
            return

        securityManager.checkOwnership(picture)

        // Delete the picture
        picture.deleted = OffsetDateTime.now()
        picture.isDeleted = true
        pictureDao.save(picture)

        // Reset thumbnail
        val product = picture.product
        if (picture.id == product.thumbnail?.id) {
            product.thumbnail = product.pictures.find { !it.isDeleted && it.id != picture.id }
            if (product.thumbnail?.isDeleted == true)
                product.thumbnail = null

            dao.save(product)
        }

        publish(EventURN.PRODUCT_UPDATED, product)
    }
}
