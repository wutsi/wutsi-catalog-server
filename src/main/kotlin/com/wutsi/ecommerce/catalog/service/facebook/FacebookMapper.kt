package com.wutsi.ecommerce.catalog.service.facebook

import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.platform.tenant.dto.Tenant
import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class FacebookMapper {
    fun toFacebookProduct(product: ProductEntity, tenant: Tenant) = FacebookProduct(
        id = product.id.toString(),
        title = product.title,
        description = toString(product.summary) ?: product.title,
        availability = if (product.quantity > 0) "in stock" else "out of stock",
        condition = "new",
        price = formatMoney(product.comparablePrice ?: product.price!!, tenant),
        salesPrice = product.comparablePrice?.let { formatMoney(product.price!!, tenant) },
        link = "${tenant.webappUrl}/product?id=${product.id}",
        imageLink = product.thumbnail?.url,
        brand = "Unknown Brand"
    )

    private fun formatMoney(amount: Double, tenant: Tenant): String =
        DecimalFormat(tenant.numberFormat).format(amount) + " ${tenant.currency}"

    private fun toString(value: String?): String? =
        if (value.isNullOrEmpty()) null else value
}
