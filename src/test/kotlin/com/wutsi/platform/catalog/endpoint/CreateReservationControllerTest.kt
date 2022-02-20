package com.wutsi.platform.catalog.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.dao.ReservationProductRepository
import com.wutsi.platform.catalog.dao.ReservationRepository
import com.wutsi.platform.catalog.dto.CreateReservationRequest
import com.wutsi.platform.catalog.dto.CreateReservationResponse
import com.wutsi.platform.catalog.dto.ReservationProduct
import com.wutsi.platform.catalog.entity.ReservationStatus
import com.wutsi.platform.catalog.error.ErrorURN
import com.wutsi.platform.core.error.ErrorResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/CreateReservationController.sql"])
public class CreateReservationControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var productDao: ProductRepository

    @Autowired
    private lateinit var reservationDao: ReservationRepository

    @Autowired
    private lateinit var reservationProductDao: ReservationProductRepository

    @Test
    public fun reserve() {
        val request = CreateReservationRequest(
            orderId = 1111,
            products = listOf(
                ReservationProduct(productId = 100, quantity = 10),
                ReservationProduct(productId = 101, quantity = 1)
            )
        )
        val response = rest.postForEntity(url(), request, CreateReservationResponse::class.java)

        assertEquals(200, response.statusCodeValue)

        val id = response.body!!.id
        val reservation = reservationDao.findById(id)
        assertTrue(reservation.isPresent)
        assertEquals(request.orderId, reservation.get().orderId)
        assertEquals(TENANT_ID, reservation.get().tenantId)
        assertEquals(ReservationStatus.CREATED, reservation.get().status)

        val rproducts = reservationProductDao.findByReservation(reservation.get()).sortedBy { it.productId }
        assertEquals(2, rproducts.size)
        assertEquals(100L, rproducts[0].productId)
        assertEquals(10, rproducts[0].quantity)

        assertEquals(101L, rproducts[1].productId)
        assertEquals(1, rproducts[1].quantity)

        assertEquals(90, productDao.findById(100).get().quantity)
        assertEquals(0, productDao.findById(101).get().quantity)
    }

    @Test
    public fun outOfStock() {
        val request = CreateReservationRequest(
            orderId = 1111,
            products = listOf(
                ReservationProduct(productId = 200, quantity = 10),
                ReservationProduct(productId = 201, quantity = 11)
            )
        )
        val ex = assertThrows<HttpClientErrorException> {
            rest.postForEntity(url(), request, CreateReservationResponse::class.java)
        }

        assertEquals(409, ex.rawStatusCode)

        val response = ObjectMapper().readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        assertEquals(ErrorURN.OUT_OF_STOCK_ERROR.urn, response.error.code)

        assertEquals(100, productDao.findById(200).get().quantity)
        assertEquals(1, productDao.findById(201).get().quantity)
    }

    private fun url() = "http://localhost:$port/v1/reservations"
}
