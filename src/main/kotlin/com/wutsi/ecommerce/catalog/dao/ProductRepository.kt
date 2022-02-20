package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.entity.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<ProductEntity, Long>
