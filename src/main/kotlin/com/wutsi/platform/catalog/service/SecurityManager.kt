package com.wutsi.platform.catalog.service

import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.entity.PictureEntity
import com.wutsi.platform.catalog.entity.ProductEntity
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.exception.ForbiddenException
import com.wutsi.platform.core.security.WutsiPrincipal
import com.wutsi.platform.core.tracing.TracingContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityManager(
    private val tracingContext: TracingContext
) {
    fun checkOwnership(product: ProductEntity) {
        if (product.accountId != accountId())
            throw ForbiddenException(
                error = Error(
                    code = ErrorURN.ILLEGAL_PRODUCT_ACCESS.urn
                )
            )
    }

    fun checkOwnership(picture: PictureEntity) {
        if (picture.product.accountId != accountId())
            throw ForbiddenException(
                error = Error(
                    code = ErrorURN.ILLEGAL_PICTURE_ACCESS.urn
                )
            )
    }

    fun checkOwnership(category: CategoryEntity) {
        if (category.accountId != accountId())
            throw ForbiddenException(
                error = Error(
                    code = ErrorURN.ILLEGAL_CATEGORY_ACCESS.urn
                )
            )
    }

    fun tenantId(): Long =
        tracingContext.tenantId()!!.toLong()

    fun accountId(): Long =
        principal().id.toLong()

    private fun principal(): WutsiPrincipal {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal
        return principal as WutsiPrincipal
    }
}
