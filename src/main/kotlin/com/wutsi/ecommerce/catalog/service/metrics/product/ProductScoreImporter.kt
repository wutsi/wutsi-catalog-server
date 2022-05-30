package com.wutsi.ecommerce.catalog.service.metrics.product

import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ProductScoreImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractProductScoreImporter(ds, storage)
