package com.wutsi.ecommerce.catalog.dto

public data class SearchProductRequest(
    public val productIds: List<Long> = emptyList(),
    public val categoryIds: List<Long> = emptyList(),
    public val visible: Boolean? = null,
    public val accountId: Long? = null,
    public val limit: Int = 30,
    public val offset: Int = 0
)
