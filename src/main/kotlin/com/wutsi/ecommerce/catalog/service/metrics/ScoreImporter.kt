package com.wutsi.ecommerce.catalog.service.metrics

import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ScoreImporter(
    ds: DataSource,
    storage: StorageService,
) : AbstractScoreImporter(ds, storage)
