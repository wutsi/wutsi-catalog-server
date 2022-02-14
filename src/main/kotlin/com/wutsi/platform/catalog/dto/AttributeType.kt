package com.wutsi.platform.catalog.dto

import kotlin.Long
import kotlin.String

public data class AttributeType(
    public val id: Long = 0,
    public val name: String = "",
    public val title: String = "",
    public val type: String = ""
)
