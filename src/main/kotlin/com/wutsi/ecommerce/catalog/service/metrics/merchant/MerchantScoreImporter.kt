package com.wutsi.ecommerce.catalog.service.metrics.merchant

import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class MerchantScoreImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractMerchantScoreImporter(ds, storage)
