package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.CategoryRepository
import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.entity.CategoryEntity
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.ecommerce.catalog.entity.ProductType
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.ecommerce.catalog.error.PublishError
import com.wutsi.ecommerce.catalog.event.EventURN
import com.wutsi.ecommerce.catalog.event.ProductEventPayload
import com.wutsi.ecommerce.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.ConflictException
import com.wutsi.platform.core.error.exception.NotFoundException
import com.wutsi.platform.core.stream.EventStream
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AbstractProductDelegate {
    @Autowired
    protected lateinit var dao: ProductRepository

    @Autowired
    protected lateinit var securityManager: SecurityManager

    @Autowired
    protected lateinit var categoryDao: CategoryRepository

    @Autowired
    protected lateinit var sectionDao: SectionRepository

    @Autowired
    protected lateinit var eventStream: EventStream

    fun getProduct(id: Long, checkOwnership: Boolean = true): ProductEntity {
        val product = dao.findById(id)
            .orElseThrow {
                notFound(id)
            }

        if (product.isDeleted)
            throw notFound(id)

        if (checkOwnership)
            securityManager.checkOwnership(product)

        securityManager.checkTenant(product)

        return product
    }

    fun getCategory(id: Long): CategoryEntity =
        categoryDao.findById(id)
            .orElseThrow {
                NotFoundException(
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

    protected fun publish(eventType: EventURN, product: ProductEntity) {
        try {
            eventStream.publish(
                eventType.urn,
                ProductEventPayload(id = product.id ?: -1, accountId = product.accountId)
            )
        } catch (ex: Exception) {
            LoggerFactory.getLogger(javaClass).warn("Unable to publish $eventType for Product#${product.id}", ex)
        }
    }

    protected fun checkPublish(product: ProductEntity) {
        val errors = mutableListOf<PublishError>()
        if (product.title.trim().isNullOrEmpty())
            errors.add(PublishError.MISSING_TITLE)
        if (product.type == ProductType.NUMERIC && product.numericFileUrl.isNullOrEmpty())
            errors.add(PublishError.MISSING_NUMERIC_FILE)
        if (product.pictures.filter { !it.isDeleted }.isEmpty())
            errors.add(PublishError.MISSING_PICTURE)
        if (product.summary?.trim().isNullOrEmpty())
            errors.add(PublishError.MISSING_SUMMARY)

        if (errors.isNotEmpty())
            throw ConflictException(
                error = Error(
                    code = ErrorURN.PUBLISH_ERROR.urn,
                    data = mapOf(
                        "publishing-errors" to errors
                    )
                )
            )
    }

    private fun notFound(id: Long) = NotFoundException(
        error = Error(
            code = ErrorURN.PRODUCT_NOT_FOUND.urn,
            parameter = Parameter(
                name = "id",
                type = ParameterType.PARAMETER_TYPE_PATH,
                value = id
            )
        )
    )
}
