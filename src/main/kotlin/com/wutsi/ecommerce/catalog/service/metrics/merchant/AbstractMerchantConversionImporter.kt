package com.wutsi.ecommerce.catalog.service.metrics.merchant

import com.wutsi.ecommerce.catalog.service.metrics.AbstractConversionImporter
import com.wutsi.ecommerce.catalog.service.metrics.CsvMetric
import com.wutsi.platform.core.storage.StorageService
import java.sql.PreparedStatement
import javax.sql.DataSource

abstract class AbstractMerchantConversionImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractConversionImporter(ds, storage) {
    override fun table(): String = "T_MERCHANT"
    override fun id(): String = "account_id"
    
    override fun toKey(item: CsvMetric): String =
        item.merchantId ?: ""

    override fun map(item: CsvMetric, stmt: PreparedStatement) {
        stmt.setLong(1, item.merchantId?.toLong() ?: -1)
    }
}
