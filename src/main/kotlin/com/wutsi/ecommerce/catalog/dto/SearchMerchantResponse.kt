package com.wutsi.ecommerce.catalog.dto

import kotlin.collections.List

public data class SearchMerchantResponse(
    public val merchants: List<MerchantSummary> = emptyList()
)
