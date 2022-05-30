package com.wutsi.ecommerce.catalog.dto

import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.collections.List

public data class SearchMerchantRequest(
    public val cityId: Long? = null,
    public val accountIds: List<Long> = emptyList(),
    public val withPublishedProducts: Boolean? = null,
    public val limit: Int = 30,
    public val offset: Int = 0
)
