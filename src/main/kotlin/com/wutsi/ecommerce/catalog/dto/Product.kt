package com.wutsi.ecommerce.catalog.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

data class Product(
    val id: Long = 0,
    val accountId: Long = 0,
    val thumbnail: PictureSummary? = null,
    val pictures: List<PictureSummary> = emptyList(),
    val category: CategorySummary = CategorySummary(),
    val subCategory: CategorySummary = CategorySummary(),
    val title: String = "",
    val summary: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val comparablePrice: Double? = null,
    val currency: String = "",
    val visible: Boolean = false,
    val categoryId: Long = 0,
    val subCategoryId: Long = 0,
    val quantity: Int = 0,
    val maxOrder: Int? = null,
    val type: String = "",
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") val created: OffsetDateTime = OffsetDateTime.now(),
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") val updated: OffsetDateTime = OffsetDateTime.now(),
    val numericFileUrl: String? = null,
    val sections: List<SectionSummary> = emptyList()
)
