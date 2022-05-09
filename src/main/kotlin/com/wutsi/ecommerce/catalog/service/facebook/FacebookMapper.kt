package com.wutsi.ecommerce.catalog.service.facebook

import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.platform.account.dto.Account
import com.wutsi.platform.tenant.dto.Tenant
import org.springframework.stereotype.Service
import java.text.DecimalFormat
import java.util.Locale

@Service
class FacebookMapper {
    fun toFacebookProduct(product: ProductEntity, account: Account, tenant: Tenant) = FacebookProduct(
        id = product.id.toString(),
        title = capitalize(product.title),
        description = toString(product.summary) ?: capitalize(product.title),
        availability = if (product.quantity > 0) "in stock" else "out of stock",
        condition = "new",
        price = formatMoney(product.comparablePrice ?: product.price!!, tenant),
        salesPrice = product.comparablePrice?.let { formatMoney(product.price!!, tenant) },
        link = "${tenant.webappUrl}/product?id=${product.id}",
        imageLink = product.thumbnail?.url,
        brand = account.displayName,
        additionalImageLink = product.pictures
            .filter { it.id != product.thumbnail?.id }
            .filter { !it.isDeleted }
            .map { it.url }
            .take(20)
    )

    private fun capitalize(value: String): String =
        value.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    private fun formatMoney(amount: Double, tenant: Tenant): String =
        DecimalFormat(tenant.numberFormat).format(amount) + " ${tenant.currency}"

    private fun toString(value: String?): String? =
        if (value.isNullOrEmpty()) null else value
}
