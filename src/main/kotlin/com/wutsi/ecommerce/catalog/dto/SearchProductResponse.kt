package com.wutsi.ecommerce.catalog.dto

public data class SearchProductResponse(
    public val products: List<ProductSummary> = emptyList()
)
