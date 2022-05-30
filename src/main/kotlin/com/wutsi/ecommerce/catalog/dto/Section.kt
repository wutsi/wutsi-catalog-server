package com.wutsi.ecommerce.catalog.dto

import kotlin.Int
import kotlin.Long
import kotlin.String

public data class Section(
    public val id: Long = 0,
    public val title: String = "",
    public val sortOrder: Int = 0
)
