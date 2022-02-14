package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dto.CreateProductRequest
import com.wutsi.platform.catalog.dto.CreateProductResponse
import com.wutsi.platform.catalog.entity.ProductEntity
import com.wutsi.platform.tenant.WutsiTenantApi
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
public class CreateProductDelegate(
    private val tenantApi: WutsiTenantApi
) : AbstractProductDelegate() {
    @Transactional
    public fun invoke(request: CreateProductRequest): CreateProductResponse {
        val tenantId = securityManager.tenantId()
        val tenant = tenantApi.getTenant(tenantId).tenant

        val product = dao.save(
            ProductEntity(
                tenantId = tenantId,
                accountId = securityManager.accountId(),
                currency = tenant.currency,
                title = request.title,
                summary = request.summary,
                description = request.description,
                comparablePrice = request.comparablePrice,
                price = request.price,
                visible = true,
                created = OffsetDateTime.now(),
                updated = OffsetDateTime.now(),
                category = getCategory(request.categoryId),
                subCategory = getCategory(request.subCategoryId)
            )
        )

        return CreateProductResponse(product.id ?: -1)
    }
}
