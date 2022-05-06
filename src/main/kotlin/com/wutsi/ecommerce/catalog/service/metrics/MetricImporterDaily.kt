package com.wutsi.ecommerce.catalog.service.metrics

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import java.sql.PreparedStatement
import javax.sql.DataSource

@Service
class MetricImporterDaily(
    ds: DataSource,
    storage: StorageService,
) : AbstractMetricImporterDaily(ds, storage) {
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
