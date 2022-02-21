package com.wutsi.ecommerce.catalog.dto

import kotlin.String
import kotlin.collections.List

public data class CreateReservationRequest(
    public val orderId: String = "",
    public val products: List<ReservationProduct> = emptyList()
)
