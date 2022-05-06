package com.wutsi.ecommerce.catalog.service.metrics

import com.wutsi.ecommerce.catalog.service.AbstractCsvMapper

class CsvMetricMapper : AbstractCsvMapper<CsvMetric>() {
    override fun map(col: Array<String>): CsvMetric =
        CsvMetric(
            time = getLong("time", col),
            tenantId = getString("tenantid", col),
            merchantId = getString("merchantid", col),
            productId = getString("productid", col),
            value = getLong("value", col) ?: 0
        )
}
