package com.wutsi.ecommerce.catalog.service.metrics.merchant

import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class MerchantMetricImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractMerchantMetricImporter(ds, storage)
