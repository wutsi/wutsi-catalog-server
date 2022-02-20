package com.wutsi.ecommerce.catalog.dto

public data class CategorySummary(
    public val id: Long = 0,
    public val title: String = "",
    public val parentId: Long? = null
)
