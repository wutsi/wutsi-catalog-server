package com.wutsi.ecommerce.catalog.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_MERCHANT")
data class MerchantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val tenantId: Long = -1,
    val accountId: Long = -1,
    var cityId: Long? = null,
    var isEnabled: Boolean = false,

    val totalViews: Long = 0,
    val totalShares: Long = 0,
    val totalChats: Long = 0,
    val totalOrders: Long = 0,
    val totalSales: Long = 0,
    val conversion: Double = 0.0,
    val score: Double = 0.0,
)
