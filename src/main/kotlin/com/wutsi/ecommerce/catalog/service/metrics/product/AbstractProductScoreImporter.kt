package com.wutsi.ecommerce.catalog.service.metrics.product

import com.wutsi.ecommerce.catalog.service.metrics.AbstractScoreImporter
import com.wutsi.platform.core.storage.StorageService
import javax.sql.DataSource

abstract class AbstractProductScoreImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractScoreImporter(ds, storage) {
    override fun table() = "T_PRODUCT"
}
