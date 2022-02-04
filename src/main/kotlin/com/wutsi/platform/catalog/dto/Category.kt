package com.wutsi.platform.catalog.dto

import kotlin.Boolean
import kotlin.Long
import kotlin.String

public data class Category(
    public val id: Long = 0,
    public val title: String = "",
    public val visible: Boolean = false
)
