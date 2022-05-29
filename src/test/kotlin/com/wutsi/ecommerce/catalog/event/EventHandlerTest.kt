package com.wutsi.ecommerce.catalog.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.platform.account.event.AccountDeletedPayload
import com.wutsi.platform.account.event.AccountUpdatedPayload
import com.wutsi.platform.core.stream.Event
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class EventHandlerTest {
    @MockBean
    private lateinit var merchant: MerchantEventHandler

    @Autowired
    private lateinit var handler: EventHandler

    @Test
    fun onAccountDeleted() {
        val payload = AccountDeletedPayload(555)
        val event = Event(
            type = com.wutsi.platform.account.event.EventURN.ACCOUNT_DELETED.urn,
            payload = ObjectMapper().writeValueAsString(payload)
        )
        handler.onEvent(event)

        verify(merchant).onAccountDeleted(payload)
    }

    @Test
    fun onAccountUpdated() {
        val payload = AccountUpdatedPayload(555, "xxx")
        val event = Event(
            type = com.wutsi.platform.account.event.EventURN.ACCOUNT_UPDATED.urn,
            payload = ObjectMapper().writeValueAsString(payload)
        )
        handler.onEvent(event)

        verify(merchant).onAccountUpdated(payload)
    }
}
