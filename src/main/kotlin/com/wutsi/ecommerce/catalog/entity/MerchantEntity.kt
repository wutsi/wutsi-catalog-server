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
    val productCount: Int = 0,
    val publishedProductCount: Int = 0,
)
