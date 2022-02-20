package com.wutsi.ecommerce.catalog.dto

public data class CreateReservationRequest(
    public val orderId: Long = 0,
    public val products: List<ReservationProduct> = emptyList()
)
