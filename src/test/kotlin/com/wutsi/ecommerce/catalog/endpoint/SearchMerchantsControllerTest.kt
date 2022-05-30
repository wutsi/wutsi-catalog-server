package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.dto.SearchMerchantRequest
import com.wutsi.ecommerce.catalog.dto.SearchMerchantResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/SearchMerchantsController.sql"])
class SearchMerchantsControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Test
    fun all() {
        // WHEN
        val url = "http://localhost:$port/v1/merchants/search"
        val request = SearchMerchantRequest()
        val response = rest.postForEntity(url, request, SearchMerchantResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val merchants = response.body!!.merchants.sortedBy { it.id }

        assertEquals(listOf(100L, 200L, 300L), merchants.map { it.id })
    }

    @Test
    fun byCity() {
        // WHEN
        val url = "http://localhost:$port/v1/merchants/search"
        val request = SearchMerchantRequest(
            cityId = 111L
        )
        val response = rest.postForEntity(url, request, SearchMerchantResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val merchants = response.body!!.merchants.sortedBy { it.id }

        assertEquals(listOf(100L, 200L), merchants.map { it.id })
    }

    @Test
    fun withProductPublished() {
        // WHEN
        val url = "http://localhost:$port/v1/merchants/search"
        val request = SearchMerchantRequest(
            withPublishedProducts = true
        )
        val response = rest.postForEntity(url, request, SearchMerchantResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val merchants = response.body!!.merchants.sortedBy { it.id }

        assertEquals(listOf(100L, 300L), merchants.map { it.id })
    }

    @Test
    fun byACcountIds() {
        // WHEN
        val url = "http://localhost:$port/v1/merchants/search"
        val request = SearchMerchantRequest(
            accountIds = listOf(1, 3)
        )
        val response = rest.postForEntity(url, request, SearchMerchantResponse::class.java)

        // THEN
        assertEquals(200, response.statusCodeValue)

        val merchants = response.body!!.merchants.sortedBy { it.id }

        assertEquals(listOf(100L, 300L), merchants.map { it.id })
    }
}
