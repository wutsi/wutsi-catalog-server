package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.PictureRepository
import com.wutsi.ecommerce.catalog.dto.AddPictureRequest
import com.wutsi.ecommerce.catalog.dto.AddPictureResponse
import com.wutsi.ecommerce.catalog.entity.PictureEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddPictureDelegate(
    private val pictureDao: PictureRepository
) : AbstractProductDelegate() {
    @Transactional
    fun invoke(id: Long, request: AddPictureRequest): AddPictureResponse {
        val product = getProduct(id)
        val picture = pictureDao.save(
            PictureEntity(
                url = request.url,
                product = product
            )
        )

        if (product.thumbnail == null) {
            product.thumbnail = picture
            dao.save(product)
        }
        return AddPictureResponse(picture.id ?: -1)
    }
}
