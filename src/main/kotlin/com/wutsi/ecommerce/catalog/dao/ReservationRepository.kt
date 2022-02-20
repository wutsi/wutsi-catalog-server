package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.entity.ReservationEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : CrudRepository<ReservationEntity, Long>
