package com.wutsi.platform.catalog.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

public data class Product(
    public val id: Long = 0,
    public val accountId: Long = 0,
    public val thumbnail: PictureSummary? = null,
    public val pictures: List<PictureSummary> = emptyList(),
    public val category: CategorySummary = CategorySummary(),
    public val subCategory: CategorySummary = CategorySummary(),
    public val title: String = "",
    public val summary: String? = null,
    public val description: String? = null,
    public val price: Double? = null,
    public val comparablePrice: Double? = null,
    public val currency: String = "",
    public val visible: Boolean = false,
    public val categoryId: Long = 0,
    public val subCategoryId: Long = 0,
    public val quantity: Int = 0,
    public val maxOrder: Int? = null,
    public val type: String = "",
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    public val created: OffsetDateTime = OffsetDateTime.now(),
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    public val updated: OffsetDateTime = OffsetDateTime.now()
)
