package com.wutsi.ecommerce.catalog.service.metrics.merchant

import com.wutsi.ecommerce.catalog.service.metrics.AbstractScoreImporter
import com.wutsi.ecommerce.catalog.service.metrics.CsvMetric
import com.wutsi.platform.core.storage.StorageService
import java.sql.PreparedStatement
import javax.sql.DataSource

abstract class AbstractMerchantScoreImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractScoreImporter(ds, storage) {
    override fun table() = "T_MERCHANT"
    override fun id() = "account_id"

    override fun toKey(item: CsvMetric): String =
        item.merchantId ?: ""

    override fun map(item: CsvMetric, stmt: PreparedStatement) {
        stmt.setLong(1, item.merchantId?.toLong() ?: -1)
    }
}
