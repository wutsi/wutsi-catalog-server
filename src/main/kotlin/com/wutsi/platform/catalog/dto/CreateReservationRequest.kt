package com.wutsi.platform.catalog.dto

import kotlin.Long
import kotlin.collections.List

public data class CreateReservationRequest(
    public val orderId: Long = 0,
    public val products: List<ReservationProduct> = emptyList()
)
