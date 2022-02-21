package com.wutsi.ecommerce.catalog.dto

import kotlin.Long
import kotlin.String

public data class CategorySummary(
    public val id: Long = 0,
    public val title: String = "",
    public val parentId: Long? = null
)
