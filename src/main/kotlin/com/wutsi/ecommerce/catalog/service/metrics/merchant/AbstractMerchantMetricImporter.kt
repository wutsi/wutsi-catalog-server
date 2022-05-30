package com.wutsi.ecommerce.catalog.service.metrics.merchant

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.ecommerce.catalog.service.metrics.AbstractMetricImporter
import com.wutsi.ecommerce.catalog.service.metrics.CsvMetric
import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import java.sql.PreparedStatement
import javax.sql.DataSource

@Service
class AbstractMerchantMetricImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractMetricImporter(ds, storage) {
    override fun sql(type: MetricType): String {
        val column = "total_${type.name.lowercase()}s"
        return """
            UPDATE T_MERCHANT M
                SET $column=COALESCE((SELECT SUM($column) FROM T_PRODUCT P WHERE  P.is_deleted=false AND P.account_id=M.account_id), 0)
                WHERE account_id=?
        """
    }

    override fun map(item: CsvMetric, stmt: PreparedStatement) {
        stmt.setLong(1, item.merchantId?.toLong() ?: -1)
    }

    override fun toKey(item: CsvMetric): String =
        item.merchantId ?: ""
}
