package com.wutsi.ecommerce.catalog.dto

import kotlin.Long

public data class Merchant(
    public val id: Long = 0,
    public val accountId: Long = 0,
    public val cityId: Long? = null,
    public val overallMetrics: Metrics = Metrics()
)
