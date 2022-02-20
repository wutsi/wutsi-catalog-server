package com.wutsi.platform.catalog.dao

import com.wutsi.platform.catalog.entity.ReservationEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : CrudRepository<ReservationEntity, Long>
