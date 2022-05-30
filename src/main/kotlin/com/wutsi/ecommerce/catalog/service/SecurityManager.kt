package com.wutsi.ecommerce.catalog.service

import com.wutsi.ecommerce.catalog.entity.MerchantEntity
import com.wutsi.ecommerce.catalog.entity.PictureEntity
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.ecommerce.catalog.entity.SectionEntity
import com.wutsi.ecommerce.catalog.error.ErrorURN
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

    fun checkOwnership(section: SectionEntity) {
        if (section.accountId != accountId())
            throw ForbiddenException(
                error = Error(
                    code = ErrorURN.ILLEGAL_SECTION_ACCESS.urn
                )
            )
    }

    fun checkTenant(product: ProductEntity) {
        if (product.tenantId != tenantId())
            throw ForbiddenException(
                error = Error(
                    code = ErrorURN.ILLEGAL_TENANT_ACCESS.urn
                )
            )
    }

    fun checkTenant(merchant: MerchantEntity) {
        if (merchant.tenantId != tenantId())
            throw ForbiddenException(
                error = Error(
                    code = ErrorURN.ILLEGAL_TENANT_ACCESS.urn
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
