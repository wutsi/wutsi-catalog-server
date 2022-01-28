package com.wutsi.platform.catalog.dto

import javax.validation.constraints.NotBlank
import kotlin.Double
import kotlin.Long
import kotlin.String

public data class CreateProductRequest(
    @get:NotBlank
    public val title: String = "",
    @get:NotBlank
    public val summary: String = "",
    public val description: String? = null,
    public val categoryId: Long? = null,
    public val price: Double? = null,
    public val comparablePrice: Double? = null
)
