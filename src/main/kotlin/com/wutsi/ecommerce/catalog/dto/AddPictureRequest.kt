package com.wutsi.ecommerce.catalog.dto

import javax.validation.constraints.NotBlank

public data class AddPictureRequest(
    @get:NotBlank
    public val url: String = ""
)
