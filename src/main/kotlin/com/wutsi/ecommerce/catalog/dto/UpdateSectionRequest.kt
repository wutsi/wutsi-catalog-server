package com.wutsi.ecommerce.catalog.dto

import javax.validation.constraints.NotBlank
import kotlin.Int
import kotlin.String

public data class UpdateSectionRequest(
    @get:NotBlank
    public val title: String = "",
    public val sortOrder: Int = 0
)
