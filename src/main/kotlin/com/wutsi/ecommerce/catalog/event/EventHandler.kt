package com.wutsi.ecommerce.catalog.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.platform.account.event.AccountDeletedPayload
import com.wutsi.platform.account.event.AccountUpdatedPayload
import com.wutsi.platform.core.stream.Event
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class EventHandler(
    private val objectMapper: ObjectMapper,
    private val merchant: MerchantEventHandler
) {
    @EventListener
    fun onEvent(event: Event) {
        if (event.type == com.wutsi.platform.account.event.EventURN.ACCOUNT_DELETED.urn) {
            val payload = objectMapper.readValue(event.payload, AccountDeletedPayload::class.java)
            merchant.onAccountDeleted(payload)
        } else if (event.type == com.wutsi.platform.account.event.EventURN.ACCOUNT_UPDATED.urn) {
            val payload = objectMapper.readValue(event.payload, AccountUpdatedPayload::class.java)
            merchant.onAccountUpdated(payload)
        }
    }
}
