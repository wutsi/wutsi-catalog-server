package com.wutsi.ecommerce.catalog.event

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.ecommerce.catalog.dao.MerchantRepository
import com.wutsi.platform.account.WutsiAccountApi
import com.wutsi.platform.account.dto.Account
import com.wutsi.platform.account.dto.GetAccountResponse
import com.wutsi.platform.account.event.AccountDeletedPayload
import com.wutsi.platform.account.event.AccountUpdatedPayload
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/MerchantEventHandler.sql"])
internal class MerchantEventHandlerTest {
    @MockBean
    private lateinit var accountApi: WutsiAccountApi

    @Autowired
    private lateinit var handler: MerchantEventHandler

    @Autowired
    private lateinit var dao: MerchantRepository

    @Test
    fun onAccountDeleted() {
        // WHEN
        handler.onAccountDeleted(AccountDeletedPayload(900))

        // THEN
        val merchant = dao.findByAccountId(900).get()
        assertFalse(merchant.enabled)
    }

    @Test
    fun onCityChangedForMerchant() {
        // GIVEN
        val accountId = 100L
        val account = createAccount(accountId, true, true, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "city-id"))

        // THEN
        val merchant = dao.findByAccountId(accountId).get()
        assertTrue(merchant.enabled)
        assertEquals(account.cityId, merchant.cityId)
    }

    @Test
    fun onCityChangedForPersonalAccount() {
        // GIVEN
        val accountId = 101L
        val account = createAccount(accountId, false, true, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "city-id"))

        // THEN
        val merchant = dao.findByAccountId(accountId)
        assertFalse(merchant.isPresent)
    }

    @Test
    fun onBusinessAccountEnabledFirstTime() {
        // GIVEN
        val accountId = 119L
        val account = createAccount(accountId, true, true, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "business"))

        // THEN
        val merchant = dao.findByAccountId(accountId).get()
        assertTrue(merchant.enabled)
        assertEquals(account.cityId, merchant.cityId)
    }

    @Test
    fun onBusinessAccountEnabled() {
        // GIVEN
        val accountId = 110L
        val account = createAccount(accountId, true, true, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "business"))

        // THEN
        val merchant = dao.findByAccountId(accountId).get()
        assertTrue(merchant.enabled)
        assertEquals(account.cityId, merchant.cityId)
    }

    @Test
    fun onBusinessAccountDisabled() {
        // GIVEN
        val accountId = 111L
        val account = createAccount(accountId, false, true, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "business"))

        // THEN
        val merchant = dao.findByAccountId(accountId).get()
        assertFalse(merchant.enabled)
        assertEquals(account.cityId, merchant.cityId)
    }

    @Test
    fun onStoreEnabledFirstTime() {
        // GIVEN
        val accountId = 129L
        val account = createAccount(accountId, true, true, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "has-store"))

        // THEN
        val merchant = dao.findByAccountId(accountId).get()
        assertTrue(merchant.enabled)
        assertEquals(account.cityId, merchant.cityId)
    }

    @Test
    fun onStoreEnabled() {
        // GIVEN
        val accountId = 120L
        val account = createAccount(accountId, true, true, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "has-store"))

        // THEN
        val merchant = dao.findByAccountId(accountId).get()
        assertTrue(merchant.enabled)
        assertEquals(account.cityId, merchant.cityId)
    }

    @Test
    fun onStoreDisabled() {
        // GIVEN
        val accountId = 121L
        val account = createAccount(accountId, true, false, 555)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        handler.onAccountUpdated(AccountUpdatedPayload(accountId, "has-store"))

        // THEN
        val merchant = dao.findByAccountId(accountId).get()
        assertFalse(merchant.enabled)
        assertEquals(account.cityId, merchant.cityId)
    }

    private fun createAccount(id: Long, business: Boolean, hasStore: Boolean, cityId: Long? = null) = Account(
        id = id,
        business = business,
        hasStore = hasStore,
        cityId = cityId
    )
}
