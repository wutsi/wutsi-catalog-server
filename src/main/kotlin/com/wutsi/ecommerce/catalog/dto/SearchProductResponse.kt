package com.wutsi.ecommerce.catalog.dto

import kotlin.collections.List

public data class SearchProductResponse(
    public val products: List<ProductSummary> = emptyList()
)
