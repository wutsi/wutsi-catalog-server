package com.wutsi.ecommerce.catalog.service.metrics.product

import com.wutsi.ecommerce.catalog.service.metrics.AbstractConversionImporter
import com.wutsi.platform.core.storage.StorageService
import javax.sql.DataSource

abstract class AbstractProductConversionImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractConversionImporter(ds, storage) {
    override fun table(): String = "T_PRODUCT"
    override fun id(): String = "id"
}
