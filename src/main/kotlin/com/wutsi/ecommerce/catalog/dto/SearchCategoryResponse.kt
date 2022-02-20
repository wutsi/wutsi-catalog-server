package com.wutsi.ecommerce.catalog.dto

public data class SearchCategoryResponse(
    public val categories: List<CategorySummary> = emptyList()
)
