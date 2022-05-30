package com.wutsi.ecommerce.catalog.dto

import kotlin.Long

public data class MerchantSummary(
    public val id: Long = 0,
    public val accountId: Long = 0,
    public val cityId: Long? = null
)
