package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dto.SearchMerchantRequest
import com.wutsi.ecommerce.catalog.dto.SearchMerchantResponse
import com.wutsi.ecommerce.catalog.entity.MerchantEntity
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.service.SecurityManager
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.Query

@Service
class SearchMerchantsDelegate(
    private val securityManager: SecurityManager,
    private val em: EntityManager,
    private val logger: KVLogger
) {
    fun invoke(request: SearchMerchantRequest): SearchMerchantResponse {
        logger.add("offset", request.offset)
        logger.add("limit", request.limit)
        logger.add("city_id", request.cityId)
        logger.add("with_published_products", request.withPublishedProducts)

        val merchants = search(request, securityManager.tenantId())
        logger.add("count", merchants.size)
        return SearchMerchantResponse(
            merchants = merchants.map { it.toMerchantSummary() }
        )
    }

    fun search(request: SearchMerchantRequest, tenantId: Long?): List<MerchantEntity> {
        val query = em.createQuery(sql(request, tenantId))
        parameters(request, query, tenantId)
        return query
            .setFirstResult(request.offset)
            .setMaxResults(request.limit)
            .resultList as List<MerchantEntity>
    }

    private fun sql(request: SearchMerchantRequest, tenantId: Long?): String {
        val select = select()
        val where = where(request, tenantId)
        return if (where.isNullOrEmpty())
            select
        else
            "$select WHERE $where"
    }

    private fun select(): String =
        "SELECT M FROM MerchantEntity M"

    private fun where(request: SearchMerchantRequest, tenantId: Long?): String {
        val criteria = mutableListOf("M.isEnabled=:is_enabled")

        if (tenantId != null)
            criteria.add("M.tenantId = :tenant_id")

        if (request.cityId != null)
            criteria.add("M.cityId = :city_id")

        if (request.accountIds.isNotEmpty())
            criteria.add("M.accountId IN :account_ids")

        if (request.withPublishedProducts == true)
            criteria.add(
                """
                    EXISTS (
                        SELECT P FROM ProductEntity P
                        WHERE
                            P.status=:status AND
                            P.isDeleted=:is_deleted AND
                            P.accountId=M.accountId
                    )
                """.trimIndent()
            )

        return criteria.joinToString(separator = " AND ")
    }

    private fun parameters(request: SearchMerchantRequest, query: Query, tenantId: Long?) {
        query.setParameter("is_enabled", true)

        if (tenantId != null)
            query.setParameter("tenant_id", tenantId)

        if (request.cityId != null)
            query.setParameter("city_id", request.cityId)

        if (request.accountIds.isNotEmpty())
            query.setParameter("account_ids", request.accountIds)

        if (request.withPublishedProducts != null) {
            query.setParameter("status", ProductStatus.PUBLISHED)
            query.setParameter("is_deleted", false)
        }
    }
}
