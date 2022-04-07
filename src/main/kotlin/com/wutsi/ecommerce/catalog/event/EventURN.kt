package com.wutsi.ecommerce.catalog.event

enum class EventURN(val urn: String) {
    PRODUCT_PUBLISHED("urn:wutsi:event:catalog:product-published"),
    PRODUCT_UNPUBLISHED("urn:wutsi:event:catalog:product-unpublished"),
}
