package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class RemoveFromSectionDelegate(private val productDao: ProductRepository) : AbstractSectionDelegate() {
    @Transactional
    fun invoke(id: Long, productId: Long) {
        val section = findById(id)
        securityManager.checkOwnership(section)

        val product = productDao.findById(productId)
            .orElseThrow {
                NotFoundException(
                    error = Error(
                        code = ErrorURN.PRODUCT_NOT_FOUND.urn,
                        parameter = Parameter(
                            name = "id",
                            type = ParameterType.PARAMETER_TYPE_PATH,
                            value = productId
                        )
                    )
                )
            }

        if (section.products.remove(product)) {
            section.productCount = section.products.filter { !it.isDeleted }.size
            dao.save(section)
        }
    }
}
