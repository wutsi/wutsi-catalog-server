package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.ReservationProductRepository
import com.wutsi.platform.catalog.dao.ReservationRepository
import com.wutsi.platform.catalog.dto.CreateReservationRequest
import com.wutsi.platform.catalog.dto.CreateReservationResponse
import com.wutsi.platform.catalog.dto.ReservationProduct
import com.wutsi.platform.catalog.entity.ReservationEntity
import com.wutsi.platform.catalog.entity.ReservationProductEntity
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.ConflictException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CreateReservationDelegate(
    private val reservationDao: ReservationRepository,
    private val reservationProductDao: ReservationProductRepository,
) : AbstractProductDelegate() {
    @Transactional
    fun invoke(request: CreateReservationRequest): CreateReservationResponse {
        val tenantId = securityManager.tenantId()
        val reservation = reservationDao.save(
            ReservationEntity(
                orderId = request.orderId,
                tenantId = tenantId,
            )
        )

        request.products.forEach { reserve(reservation, it) }

        return CreateReservationResponse(id = reservation.id!!)
    }

    private fun reserve(reservation: ReservationEntity, item: ReservationProduct): ReservationProductEntity {
        val product = getProduct(item.productId, false)

        if (product.quantity < item.quantity)
            throw ConflictException(
                error = Error(
                    code = ErrorURN.OUT_OF_STOCK_ERROR.urn,
                    parameter = Parameter(
                        name = "productId",
                        value = item.productId,
                        type = ParameterType.PARAMETER_TYPE_PAYLOAD
                    ),
                    data = mapOf(
                        "quantityInStock" to product.quantity,
                        "quantityToReserve" to item.quantity,
                    )
                )
            )

        // Decrement inventory
        product.quantity -= item.quantity
        dao.save(product)

        // Reserve
        return reservationProductDao.save(
            ReservationProductEntity(
                reservation = reservation,
                product = product,
                quantity = item.quantity
            )
        )
    }
}
