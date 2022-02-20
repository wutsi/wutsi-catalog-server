package com.wutsi.ecommerce.catalog.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "T_RESERVATION_PRODUCT")
data class ReservationProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk")
    val product: ProductEntity = ProductEntity(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_fk")
    val reservation: ReservationEntity = ReservationEntity(),

    val quantity: Int = 0
)
