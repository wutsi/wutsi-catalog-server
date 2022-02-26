package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.ReservationProductRepository
import com.wutsi.ecommerce.catalog.dao.ReservationRepository
import com.wutsi.ecommerce.catalog.dto.CreateReservationRequest
import com.wutsi.ecommerce.catalog.dto.CreateReservationResponse
import com.wutsi.ecommerce.catalog.dto.ReservationProduct
import com.wutsi.ecommerce.catalog.entity.ReservationEntity
import com.wutsi.ecommerce.catalog.entity.ReservationProductEntity
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.ConflictException
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CreateReservationDelegate(
    private val reservationDao: ReservationRepository,
    private val reservationProductDao: ReservationProductRepository,
    private val logger: KVLogger
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
        logger.add("reservation_id", reservation.id)

        request.products.forEach { reserve(reservation, it) }

        return CreateReservationResponse(id = reservation.id!!)
    }

    private fun reserve(reservation: ReservationEntity, item: ReservationProduct): ReservationProductEntity {
        logger.add("product_${item.productId}_quantity_to_reserve", item.quantity)

        val product = getProduct(item.productId, false)
        logger.add("product_${item.productId}_quantity_in_stock", product.quantity)

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
