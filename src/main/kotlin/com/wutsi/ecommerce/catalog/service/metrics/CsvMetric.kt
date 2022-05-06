package com.wutsi.ecommerce.catalog.service.metrics

data class CsvMetric(
    var time: Long? = null,
    var tenantId: String? = null,
    var merchantId: String? = null,
    var productId: String? = null,
    var value: Long = 1
)
