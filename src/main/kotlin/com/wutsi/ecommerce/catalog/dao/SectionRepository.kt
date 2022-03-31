package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.entity.SectionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SectionRepository : CrudRepository<SectionEntity, Long> {
    fun findByAccountId(accountId: Long): List<SectionEntity>
}
