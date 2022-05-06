package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.SearchProductRequest
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.service.facebook.FacebookMapper
import com.wutsi.ecommerce.catalog.service.facebook.FacebookProduct
import com.wutsi.ecommerce.catalog.service.facebook.FacebookWriter
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.tenant.WutsiTenantApi
import com.wutsi.platform.tenant.dto.Tenant
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class FacebookFeedDelegate(
    private val search: SearchProductsDelegate,
    private val tenantApi: WutsiTenantApi,
    private val response: HttpServletResponse,
    private val logger: KVLogger,
    private val mapper: FacebookMapper
) {
    fun invoke(accountId: Long) {
        val limit = 100
        var offset = 0
        val fbProducts = mutableListOf<FacebookProduct>()
        var tenant: Tenant? = null

        while (true) {
            // Product
            val products = search.search(
                request = SearchProductRequest(
                    limit = limit,
                    offset = offset,
                    accountId = accountId,
                    status = ProductStatus.PUBLISHED.name,
                ),
                tenantId = null
            )
            if (products.isEmpty())
                break

            // Tenant
            if (tenant == null)
                tenant = tenantApi.getTenant(products[0].tenantId).tenant

            // Map
            fbProducts.addAll(products.map { mapper.toFacebookProduct(it, tenant) })

            // Next
            if (products.size < limit)
                break
            else
                offset += limit
        }

        logger.add("product_count", fbProducts.size)
        FacebookWriter().write(fbProducts, response.outputStream)
    }
}
