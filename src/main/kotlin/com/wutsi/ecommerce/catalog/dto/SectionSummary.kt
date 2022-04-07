package com.wutsi.ecommerce.catalog.dto

import kotlin.Int
import kotlin.Long
import kotlin.String

public data class SectionSummary(
    public val id: Long = 0,
    public val title: String = "",
    public val sortOrder: Int = 0,
    public val productCount: Int = 0,
    public val publishedProductCount: Int = 0
)
