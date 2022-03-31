package com.wutsi.ecommerce.catalog.dto

import javax.validation.constraints.NotBlank
import kotlin.String

public data class CreateSectionRequest(
    @get:NotBlank
    public val title: String = ""
)
