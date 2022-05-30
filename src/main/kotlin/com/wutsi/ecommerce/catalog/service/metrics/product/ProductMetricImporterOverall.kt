package com.wutsi.ecommerce.catalog.service.metrics.product

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.AbstractMetricImporter
import com.wutsi.ecommerce.catalog.service.metrics.CsvMetric
import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import java.net.URL
import java.sql.PreparedStatement
import java.time.LocalDate
import javax.sql.DataSource

@Service
class ProductMetricImporterOverall(
    ds: DataSource,
    storage: StorageService,
) : AbstractMetricImporter(ds, storage) {
    override fun sql(type: MetricType): String {
        val column = "total_${type.name.lowercase()}s"
        return """
            UPDATE T_PRODUCT
                SET $column=?
                WHERE id=?
        """
    }

    override fun map(item: CsvMetric, stmt: PreparedStatement) {
        stmt.setLong(1, item.value)
        stmt.setLong(2, item.productId?.toLong() ?: -1)
    }

    override fun toURL(date: LocalDate, type: MetricType): URL =
        storage.toURL(
            "aggregates/overall/" + type.name.lowercase() + ".csv"
        )
}
