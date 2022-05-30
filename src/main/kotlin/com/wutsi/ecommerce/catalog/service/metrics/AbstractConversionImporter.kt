package com.wutsi.ecommerce.catalog.service.metrics

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import java.sql.PreparedStatement
import javax.sql.DataSource

abstract class AbstractConversionImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractMetricImporter(ds, storage) {
    protected abstract fun table(): String
    protected abstract fun id(): String

    override fun sql(type: MetricType): String =
        """
            UPDATE ${table()}
                SET conversion=
                    CASE total_views
                        WHEN 0 THEN 0
                        ELSE CAST (total_orders as DECIMAL)/total_views
                    END
                WHERE ${id()}=?
        """

    override fun map(item: CsvMetric, stmt: PreparedStatement) {
        stmt.setLong(1, item.productId?.toLong() ?: -1)
    }
}
