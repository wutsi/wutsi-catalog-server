package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dao.ProductRepository
import com.wutsi.ecommerce.catalog.dao.ReservationRepository
import com.wutsi.ecommerce.catalog.entity.ReservationStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/CancelReservationController.sql"])
public class CancelReservationControllerTest : AbstractSecuredController() {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var productDao: ProductRepository

    @Autowired
    private lateinit var reservationDao: ReservationRepository

    @Test
    public fun reserve() {
        rest.delete(url(100))

        val reservation = reservationDao.findById(100)
        assertTrue(reservation.isPresent)
        assertEquals(ReservationStatus.CANCELLED, reservation.get().status)
        assertNotNull(reservation.get().cancelled)

        assertEquals(100, productDao.findById(100).get().quantity)
        assertEquals(2, productDao.findById(101).get().quantity)
    }

    @Test
    public fun cancelled() {
        rest.delete(url(200))

        val reservation = reservationDao.findById(200)
        assertTrue(reservation.isPresent)
        assertEquals(ReservationStatus.CANCELLED, reservation.get().status)
        assertNotNull(reservation.get().cancelled)

        assertEquals(100, productDao.findById(200).get().quantity)
    }

    private fun url(id: Long) = "http://localhost:$port/v1/reservations/$id"
}
