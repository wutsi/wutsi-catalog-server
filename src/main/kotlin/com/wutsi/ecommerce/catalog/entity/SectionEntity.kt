package com.wutsi.ecommerce.catalog.entity

import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "T_SECTION")
data class SectionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val tenantId: Long = -1,
    val accountId: Long = -1,
    var title: String = "",
    var sortOrder: Int = 0,
    var isDeleted: Boolean = false,
    val created: OffsetDateTime = OffsetDateTime.now(),
    var updated: OffsetDateTime = OffsetDateTime.now(),
    var deleted: OffsetDateTime? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "T_SECTION_PRODUCT",
        joinColumns = arrayOf(JoinColumn(name = "section_fk")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "product_fk"))
    )
    var products: MutableList<ProductEntity> = mutableListOf()
)
