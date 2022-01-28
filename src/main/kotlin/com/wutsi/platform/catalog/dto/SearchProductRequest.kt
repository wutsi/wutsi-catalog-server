package com.wutsi.platform.catalog.dto

import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.collections.List

public data class SearchProductRequest(
    public val productIds: List<Long> = emptyList(),
    public val categoryIds: List<Long> = emptyList(),
    public val visible: Boolean? = null,
    public val accountId: Long? = null,
    public val limit: Int = 30,
    public val offset: Int = 0
)
