package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.entity.MetricEntity
import com.wutsi.ecommerce.catalog.entity.MetricType
import com.wutsi.ecommerce.catalog.entity.PeriodEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MetricRepository : CrudRepository<MetricEntity, Long> {
    fun findByPeriodAndProductIdAndType(period: PeriodEntity, productId: Long, type: MetricType): Optional<MetricEntity>
}
