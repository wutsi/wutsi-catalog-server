package com.wutsi.platform.catalog.dao

import com.wutsi.platform.catalog.entity.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<ProductEntity, Long>
