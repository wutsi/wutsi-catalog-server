package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.PictureRepository
import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.dto.AddPictureRequest
import com.wutsi.platform.catalog.dto.AddPictureResponse
import com.wutsi.platform.catalog.entity.PictureEntity
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddPictureDelegate(
    dao: ProductRepository,
    securityManager: SecurityManager,
    private val pictureDao: PictureRepository
) : AbstractProductDelegate(dao, securityManager) {
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
