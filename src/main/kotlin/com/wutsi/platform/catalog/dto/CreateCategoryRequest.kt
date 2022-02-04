package com.wutsi.platform.catalog.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.String

public data class CreateCategoryRequest(
    @get:NotBlank
    @get:Size(max = 100)
    public val title: String = ""
)
