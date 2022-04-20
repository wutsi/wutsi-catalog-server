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
@Table(name = "T_METRIC")
data class MetricEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_fk")
    val period: PeriodEntity = PeriodEntity(),

    val productId: Long = -1,
    val merchantId: Long = -1,
    val type: MetricType = MetricType.VIEW,
    val value: Double = 0.0,
)
