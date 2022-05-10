package com.wutsi.ecommerce.catalog.event

enum class EventURN(val urn: String) {
    PRODUCT_PUBLISHED("urn:wutsi:event:catalog:product-published"),
    PRODUCT_UNPUBLISHED("urn:wutsi:event:catalog:product-unpublished"),
    PRODUCT_CREATED("urn:wutsi:event:catalog:product-created"),
    PRODUCT_UPDATED("urn:wutsi:event:catalog:product-updated"),
    PRODUCT_DELETED("urn:wutsi:event:catalog:product-deleted")
}
