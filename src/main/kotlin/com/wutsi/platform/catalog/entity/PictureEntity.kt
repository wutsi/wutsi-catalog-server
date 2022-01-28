package com.wutsi.platform.catalog.entity

import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "T_PICTURE")
data class PictureEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne()
    @JoinColumn(name = "product_fk")
    val product: ProductEntity = ProductEntity(),

    val url: String = "",
    var isDeleted: Boolean = false,
    val created: OffsetDateTime = OffsetDateTime.now(),
    var deleted: OffsetDateTime? = null,
)
