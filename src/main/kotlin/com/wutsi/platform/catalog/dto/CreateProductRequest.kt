package com.wutsi.platform.catalog.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.Double
import kotlin.String

public data class CreateProductRequest(
    @get:NotBlank
    @get:Size(max = 100)
    public val title: String = "",
    @get:Size(max = 160)
    public val summary: String? = null,
    public val description: String? = null,
    public val price: Double? = null,
    public val comparablePrice: Double? = null
)
