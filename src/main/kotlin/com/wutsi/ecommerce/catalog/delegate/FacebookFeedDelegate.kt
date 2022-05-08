package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.SearchProductRequest
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.service.facebook.FacebookMapper
import com.wutsi.ecommerce.catalog.service.facebook.FacebookProduct
import com.wutsi.ecommerce.catalog.service.facebook.FacebookWriter
import com.wutsi.platform.account.WutsiAccountApi
import com.wutsi.platform.account.dto.Account
import com.wutsi.platform.account.entity.AccountStatus
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.tenant.WutsiTenantApi
import com.wutsi.platform.tenant.dto.Tenant
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class FacebookFeedDelegate(
    private val search: SearchProductsDelegate,
    private val tenantApi: WutsiTenantApi,
    private val accountApi: WutsiAccountApi,
    private val response: HttpServletResponse,
    private val logger: KVLogger,
    private val mapper: FacebookMapper
) {
    fun invoke(accountId: Long) {
        val account = accountApi.getAccount(accountId).account
        logger.add("account_id", accountId)
        logger.add("account_status", account.status)
        logger.add("account_display_name", account.displayName)

        if (account.status.equals(AccountStatus.ACTIVE.name, true))
            invoke(account)
        else
            write(emptyList())
    }

    private fun invoke(account: Account) {
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
                    accountId = account.id,
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
            fbProducts.addAll(products.map { mapper.toFacebookProduct(it, account, tenant) })

            // Next
            if (products.size < limit)
                break
            else
                offset += limit
        }

        logger.add("product_count", fbProducts.size)
        write(fbProducts)
    }

    private fun write(fbProducts: List<FacebookProduct>) {
        FacebookWriter().write(fbProducts, response.outputStream)
    }
}
