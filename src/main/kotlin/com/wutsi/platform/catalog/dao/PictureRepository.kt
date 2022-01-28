package com.wutsi.platform.catalog.dao

import com.wutsi.platform.catalog.entity.PictureEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PictureRepository : CrudRepository<PictureEntity, Long>
