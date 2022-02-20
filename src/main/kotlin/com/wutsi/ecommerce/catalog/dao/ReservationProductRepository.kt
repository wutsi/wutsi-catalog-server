package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.dto.ReservationProduct
import com.wutsi.ecommerce.catalog.entity.ReservationEntity
import com.wutsi.ecommerce.catalog.entity.ReservationProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationProductRepository : CrudRepository<ReservationProductEntity, Long> {
    fun findByReservation(reservation: ReservationEntity): List<ReservationProduct>
}
