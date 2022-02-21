package com.wutsi.ecommerce.catalog.dto

import kotlin.Long

public data class SearchCategoryRequest(
    public val parentId: Long? = null
)
