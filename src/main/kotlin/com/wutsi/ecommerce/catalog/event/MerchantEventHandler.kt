package com.wutsi.ecommerce.catalog.event

import com.wutsi.ecommerce.catalog.dao.MerchantRepository
import com.wutsi.ecommerce.catalog.entity.MerchantEntity
import com.wutsi.platform.account.WutsiAccountApi
import com.wutsi.platform.account.event.AccountDeletedPayload
import com.wutsi.platform.account.event.AccountUpdatedPayload
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MerchantEventHandler(
    private val accountApi: WutsiAccountApi,
    private val dao: MerchantRepository,
    private val logger: KVLogger
) {
    companion object {
        val SYNC_ATTRIBUTES = listOf(
            "business",
            "has-store",
            "city-id"
        )
    }

    @Transactional
    fun onAccountDeleted(payload: AccountDeletedPayload) {
        // Get Merchant
        val merchant = dao.findByAccountId(payload.accountId).orElseGet { null }
            ?: return
        logger.add("merchant_id", merchant.id)
        logger.add("merchant_enabled", merchant.enabled)

        // Update
        if (merchant.enabled) {
            merchant.enabled = false
            dao.save(merchant)

            logger.add("merchant_disabled", true)
        } else {
            logger.add("merchant_disabled", false)
        }
        logger.add("merchant_id", merchant.id)
    }

    @Transactional
    fun onAccountUpdated(payload: AccountUpdatedPayload) {
        if (!SYNC_ATTRIBUTES.contains(payload.attribute?.lowercase()))
            return

        // Get account
        val account = accountApi.getAccount(payload.accountId).account
        val enabled = account.business && account.hasStore
        logger.add("account_business", account.business)
        logger.add("account_has_store", account.hasStore)
        logger.add("account_city_id", account.cityId)

        // Get merchant
        val merchant = dao.findByAccountId(payload.accountId).orElseGet { null }
        if (merchant == null) {
            if (enabled) {
                val created = dao.save(
                    MerchantEntity(
                        accountId = payload.accountId,
                        enabled = true,
                        cityId = account.cityId
                    )
                )
                logger.add("merchant_updated", true)
                logger.add("merchant_id", created.id)
            } else {
                return // Not a merchant
            }
        } else {
            logger.add("merchant_id", merchant.id)
            logger.add("merchant_enabled", merchant.enabled)
            logger.add("merchant_city_id", merchant.cityId)

            // Update
            if (merchant.enabled != enabled || merchant.cityId != account.cityId) {
                merchant.cityId = account.cityId
                merchant.enabled = enabled
                dao.save(merchant)

                logger.add("merchant_updated", true)
            } else {
                logger.add("merchant_updated", false)
            }
        }
    }
}
