package com.wutsi.ecommerce.catalog.service.metrics

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import java.sql.PreparedStatement
import javax.sql.DataSource

@Service
class ScoreImporterDaily(
    ds: DataSource,
    storage: StorageService,
) : AbstractMetricImporterDaily(ds, storage) {
    override fun sql(type: MetricType): String =
        """
            UPDATE T_PRODUCT
                SET score=conversion +
                    CASE total_views
                        WHEN 0 THEN 0
                        ELSE CAST (total_shares+total_chats as DECIMAL)/total_views
                    END
                WHERE id=?
        """

    override fun map(item: CsvMetric, stmt: PreparedStatement) {
        stmt.setLong(1, item.productId?.toLong() ?: -1)
    }
}