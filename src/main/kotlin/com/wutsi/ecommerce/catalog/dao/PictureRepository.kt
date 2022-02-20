package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.entity.PictureEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PictureRepository : CrudRepository<PictureEntity, Long>
