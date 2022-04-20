package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.entity.PeriodEntity
import com.wutsi.ecommerce.catalog.entity.PeriodType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PeriodRepository : CrudRepository<PeriodEntity, Long> {
    fun findByTypeAndYear(type: PeriodType, year: Int): PeriodEntity
    fun findByTypeAndYearAndMonth(type: PeriodType, year: Int, month: Int): PeriodEntity
}
