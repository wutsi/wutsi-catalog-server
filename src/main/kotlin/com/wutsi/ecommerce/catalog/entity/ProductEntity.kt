package com.wutsi.ecommerce.catalog.entity

import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "T_PRODUCT")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val tenantId: Long = -1,
    val accountId: Long = -1,
    var title: String = "",
    var summary: String? = null,
    var description: String? = null,

    @Deprecated("No longer used - replace by publication")
    var visible: Boolean = true,

    var price: Double? = null,
    var comparablePrice: Double? = null,
    val currency: String = "",
    var isDeleted: Boolean = false,
    val created: OffsetDateTime = OffsetDateTime.now(),
    val updated: OffsetDateTime = OffsetDateTime.now(),
    var deleted: OffsetDateTime? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    val pictures: List<PictureEntity> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_fk")
    var thumbnail: PictureEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk")
    var category: CategoryEntity = CategoryEntity(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_fk")
    var subCategory: CategoryEntity = CategoryEntity(),

    var quantity: Int = 0,
    var maxOrder: Int? = null,

    @Enumerated
    var type: ProductType = ProductType.PHYSICAL,

    var numericFileUrl: String? = null,

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    val sections: List<SectionEntity> = emptyList(),

    @Enumerated
    var status: ProductStatus = ProductStatus.DRAFT,

    var published: OffsetDateTime? = null,

    val totalViews: Long = 0,
    val totalShares: Long = 0,
    val totalChats: Long = 0,
    val totalOrders: Long = 0,
    val totalSales: Long = 0,
    val conversion: Double = 0.0,
    val score: Double = 0.0,
)
