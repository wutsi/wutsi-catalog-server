package com.wutsi.ecommerce.catalog.dto

import kotlin.collections.List

public data class ListSectionResponse(
    public val sections: List<SectionSummary> = emptyList()
)
