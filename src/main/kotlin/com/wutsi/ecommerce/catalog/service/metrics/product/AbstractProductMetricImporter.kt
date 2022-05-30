package com.wutsi.ecommerce.catalog.service.metrics.product

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.AbstractMetricImporter
import com.wutsi.ecommerce.catalog.service.metrics.CsvMetric
import com.wutsi.platform.core.storage.StorageService
import java.sql.PreparedStatement
import javax.sql.DataSource

abstract class AbstractProductMetricImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractMetricImporter(ds, storage) {
    override fun sql(type: MetricType): String {
        val column = "total_${type.name.lowercase()}s"
        return """
            UPDATE T_PRODUCT
                SET $column=$column+?
                WHERE id=?
        """
    }

    override fun map(item: CsvMetric, stmt: PreparedStatement) {
        stmt.setLong(1, item.value)
        stmt.setLong(2, item.productId?.toLong() ?: -1)
    }
}
