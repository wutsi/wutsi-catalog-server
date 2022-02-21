package com.wutsi.ecommerce.catalog.entity

import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "T_RESERVATION")
data class ReservationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val orderId: String = "",
    val tenantId: Long = -1,

    var status: ReservationStatus = ReservationStatus.CREATED,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation")
    val products: List<ReservationProductEntity> = emptyList(),

    val created: OffsetDateTime = OffsetDateTime.now(),
    var confirmed: OffsetDateTime? = null,
    var cancelled: OffsetDateTime? = null
)
