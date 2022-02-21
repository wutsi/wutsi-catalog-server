package com.wutsi.ecommerce.catalog.dto

import javax.validation.constraints.NotBlank
import kotlin.String

public data class AddPictureRequest(
    @get:NotBlank
    public val url: String = ""
)
