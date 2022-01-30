package com.wutsi.platform.catalog.dto

import org.springframework.format.`annotation`.DateTimeFormat
import java.time.OffsetDateTime
import kotlin.Double
import kotlin.Long
import kotlin.String

public data class ProductSummary(
    public val id: Long = 0,
    public val thumbnail: PictureSummary? = null,
    public val title: String = "",
    public val summary: String? = null,
    public val price: Double? = null,
    public val comparablePrice: Double? = null,
    public val currency: String = "",
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    public val created: OffsetDateTime = OffsetDateTime.now(),
    @get:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    public val updated: OffsetDateTime = OffsetDateTime.now()
)
