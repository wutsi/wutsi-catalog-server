package com.wutsi.ecommerce.catalog.dto

import kotlin.Double
import kotlin.Long

public data class Metrics(
    public val totalViews: Long = 0,
    public val totalChats: Long = 0,
    public val totalShares: Long = 0,
    public val totalOrders: Long = 0,
    public val conversion: Double = 0.0,
    public val score: Double = 0.0,
    public val totalSales: Long = 0
)
