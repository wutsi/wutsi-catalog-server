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
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class FacebookFeedDelegate(
    private val search: SearchProductsDelegate,
    private val tenantApi: WutsiTenantApi,
    private val accountApi: WutsiAccountApi,
    private val response: HttpServletResponse,
    private val logger: KVLogger,
    private val mapper: FacebookMapper,
) {
    fun invoke(accountId: Long) {
        val account = accountApi.getAccount(accountId).account
        logger.add("account_id", accountId)
        logger.add("account_status", account.status)

        val products = if (account.status.equals(AccountStatus.ACTIVE.name, true))
            load(account)
        else
            emptyList()

        logger.add("product_count", products.size)
        write(products)
    }

    private fun load(account: Account): List<FacebookProduct> {
        // Product
        val products = search.search(
            request = SearchProductRequest(
                limit = 100,
                offset = 0,
                accountId = account.id,
                status = ProductStatus.PUBLISHED.name,
            ),
            tenantId = null
        )
        if (products.isEmpty())
            return emptyList()

        // Map
        val tenant = tenantApi.getTenant(products[0].tenantId).tenant
        return products.map { mapper.toFacebookProduct(it, account, tenant) }
    }

    private fun write(products: List<FacebookProduct>) {
        FacebookWriter().write(products, response.outputStream)
    }
}
