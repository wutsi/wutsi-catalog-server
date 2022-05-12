package com.wutsi.ecommerce.catalog.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_CATEGORY")
data class CategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val title: String = "",
    val titleFrench: String = "",

    @Column(name = "parent_fk")
    val parentId: Long? = null,

    var productCount: Int = 0,
    var publishedProductCount: Int = 0,
    var updateCounters: Boolean = true,
    val googleProductCategoryId: Long? = null
)
