package com.wutsi.platform.catalog.error

enum class ErrorURN(val urn: String) {
    PRODUCT_NOT_FOUND("urn:wutsi:error:catalog:product-not-found"),
    PICTURE_NOT_FOUND("urn:wutsi:error:catalog:picture-not-found"),
    CATEGORY_NOT_FOUND("urn:wutsi:error:catalog:category-not-found"),
    INVALID_ATTRIBUTE("urn:wutsi:error:catalog:invalid-product"),
    ILLEGAL_CATEGORY_ACCESS("urn:wutsi:error:catalog:illegal-category-access"),
    ILLEGAL_PRODUCT_ACCESS("urn:wutsi:error:catalog:illegal-product-access"),
    ILLEGAL_PICTURE_ACCESS("urn:wutsi:error:catalog:illegal-picture-access"),
    ILLEGAL_TENANT_ACCESS("urn:wutsi:error:catalog:illegal-tenant-access"),
}
