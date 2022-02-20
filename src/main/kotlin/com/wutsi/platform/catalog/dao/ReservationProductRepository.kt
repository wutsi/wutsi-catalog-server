package com.wutsi.platform.catalog.dao

import com.wutsi.platform.catalog.dto.ReservationProduct
import com.wutsi.platform.catalog.entity.ReservationEntity
import com.wutsi.platform.catalog.entity.ReservationProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationProductRepository : CrudRepository<ReservationProductEntity, Long> {
    fun findByReservation(reservation: ReservationEntity): List<ReservationProduct>
}
