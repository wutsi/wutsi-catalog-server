package com.wutsi.ecommerce.catalog.service.metrics.merchant

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import org.springframework.stereotype.Service
import java.net.URL
import java.time.LocalDate
import javax.sql.DataSource

@Service
class MerchantConversionImporterOverall(
    ds: DataSource,
    storage: StorageService,
) : AbstractMerchantConversionImporter(ds, storage) {
    override fun toURL(date: LocalDate, type: MetricType): URL =
        storage.toURL(
            "aggregates/overall/" + type.name.lowercase() + ".csv"
        )
}
