package com.wutsi.platform.catalog.dao

import com.wutsi.platform.catalog.entity.CategoryEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : CrudRepository<CategoryEntity, Long> {
    fun findByParentId(parentId: Long?): List<CategoryEntity>
}
