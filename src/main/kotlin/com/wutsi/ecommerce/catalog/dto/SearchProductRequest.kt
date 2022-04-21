package com.wutsi.ecommerce.catalog.dto

import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List

public data class SearchProductRequest(
    public val productIds: List<Long> = emptyList(),
    public val categoryIds: List<Long> = emptyList(),
    public val sectionId: Long? = null,
    public val visible: Boolean? = null,
    public val accountId: Long? = null,
    public val status: String? = null,
    public val limit: Int = 30,
    public val offset: Int = 0,
    public val sortBy: String? = null
)
