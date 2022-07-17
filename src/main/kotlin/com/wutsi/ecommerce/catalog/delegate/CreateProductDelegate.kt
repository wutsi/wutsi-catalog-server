package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.CreateProductRequest
import com.wutsi.ecommerce.catalog.dto.CreateProductResponse
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.ecommerce.catalog.entity.ProductType
import com.wutsi.ecommerce.catalog.event.EventURN
import com.wutsi.platform.tenant.WutsiTenantApi
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class CreateProductDelegate(
    private val tenantApi: WutsiTenantApi
) : AbstractProductDelegate() {
    @Transactional
    fun invoke(request: CreateProductRequest): CreateProductResponse {
        val tenantId = securityManager.tenantId()!!
        val tenant = tenantApi.getTenant(tenantId).tenant
        val product = dao.save(
            ProductEntity(
                tenantId = tenantId,
                accountId = securityManager.accountId(),
                currency = tenant.currency,
                title = request.title,
                summary = toString(request.summary),
                description = toString(request.description),
                comparablePrice = request.comparablePrice,
                price = request.price,
                visible = true,
                created = OffsetDateTime.now(),
                updated = OffsetDateTime.now(),
                category = getCategory(request.categoryId),
                subCategory = getCategory(request.subCategoryId),
                type = ProductType.valueOf(request.type.uppercase()),
                quantity = request.quantity,
                maxOrder = request.maxOrder,
            )
        )

        val response = CreateProductResponse(product.id ?: -1)

        publish(EventURN.PRODUCT_CREATED, product)
        return response
    }

    private fun toString(value: String?): String? =
        if (value.isNullOrEmpty())
            null
        else
            value
}
