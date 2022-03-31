package com.wutsi.ecommerce.catalog.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

data class ProductSummary(
    val id: Long = 0,
    val accountId: Long = 0,
    val thumbnail: PictureSummary? = null,
    val title: String = "",
    val summary: String? = null,
    val price: Double? = null,
    val comparablePrice: Double? = null,
    val categoryId: Long = 0,
    val subCategoryId: Long = 0,
    val quantity: Int = 0,
    val maxOrder: Int? = null,
    val type: String = "",
    val currency: String = "",
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") val created: OffsetDateTime = OffsetDateTime.now(),
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") val updated: OffsetDateTime = OffsetDateTime.now()
)
