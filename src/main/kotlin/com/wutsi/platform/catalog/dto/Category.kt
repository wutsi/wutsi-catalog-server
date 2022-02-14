package com.wutsi.platform.catalog.dto

import kotlin.Long
import kotlin.String
import kotlin.collections.List

public data class Category(
    public val id: Long = 0,
    public val title: String = "",
    public val parentId: Long? = null,
    public val attributes: List<AttributeType> = emptyList()
)
