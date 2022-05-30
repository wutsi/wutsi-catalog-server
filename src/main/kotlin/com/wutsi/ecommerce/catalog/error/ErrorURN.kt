package com.wutsi.ecommerce.catalog.error

enum class ErrorURN(val urn: String) {
    PRODUCT_NOT_FOUND("urn:wutsi:error:catalog:product-not-found"),
    PICTURE_NOT_FOUND("urn:wutsi:error:catalog:picture-not-found"),
    CATEGORY_NOT_FOUND("urn:wutsi:error:catalog:category-not-found"),
    MERCHANT_NOT_FOUND("urn:wutsi:error:catalog:merchant-not-found"),
    MERCHANT_DISABLED("urn:wutsi:error:catalog:merchant-disabled"),
    SECTION_NOT_FOUND("urn:wutsi:error:catalog:section-not-found"),
    RESERVATION_NOT_FOUND("urn:wutsi:error:catalog:reservation-not-found"),
    INVALID_ATTRIBUTE("urn:wutsi:error:catalog:invalid-product"),
    INVALID_SUB_CATEGORY("urn:wutsi:error:catalog:invalid-sub-category"),
    ILLEGAL_PRODUCT_ACCESS("urn:wutsi:error:catalog:illegal-product-access"),
    ILLEGAL_PICTURE_ACCESS("urn:wutsi:error:catalog:illegal-picture-access"),
    ILLEGAL_SECTION_ACCESS("urn:wutsi:error:catalog:illegal-section-access"),
    ILLEGAL_TENANT_ACCESS("urn:wutsi:error:catalog:illegal-tenant-access"),
    OUT_OF_STOCK_ERROR("urn:wutsi:error:catalog:out-of-stock"),
    PUBLISH_ERROR("urn:wutsi:error:catalog:publish-error"),
}
