package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.dao.ReservationRepository
import com.wutsi.ecommerce.catalog.entity.ReservationProductEntity
import com.wutsi.ecommerce.catalog.entity.ReservationStatus
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
public class CancelReservationDelegate(
    private val reservationDao: ReservationRepository,
    private val productDao: ProductRepository
) {
    @Transactional
    public fun invoke(id: Long) {
        val reservation = reservationDao.findById(id)
            .orElseThrow {
                NotFoundException(
                    error = Error(
                        code = ErrorURN.RESERVATION_NOT_FOUND.urn,
                        parameter = Parameter(
                            name = "id",
                            value = id,
                            type = ParameterType.PARAMETER_TYPE_PATH
                        )
                    )
                )
            }

        if (reservation.status == ReservationStatus.CANCELLED)
            return

        // Cancel reservation
        reservation.status = ReservationStatus.CANCELLED
        reservation.cancelled = OffsetDateTime.now()
        reservationDao.save(reservation)

        // Update inventory
        reservation.products.forEach {
            cancel(it)
        }
    }

    private fun cancel(item: ReservationProductEntity) {
        // Update inventory
        val product = item.product
        if (!product.isDeleted)
            product.quantity += item.quantity
        productDao.save(product)
    }
}
