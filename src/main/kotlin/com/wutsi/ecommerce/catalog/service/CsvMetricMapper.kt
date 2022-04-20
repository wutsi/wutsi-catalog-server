package com.wutsi.ecommerce.catalog.service

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
