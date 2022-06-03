package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dto.ListSectionResponse
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.entity.SectionEntity
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.Query

@Service
class ListSectionsDelegate(
    private val em: EntityManager,
    private val logger: KVLogger
) : AbstractSectionDelegate() {
    fun invoke(accountId: Long, withPublishedProducts: Boolean? = null): ListSectionResponse {
        val sections = search(accountId, withPublishedProducts)

        logger.add("account_id", accountId)
        logger.add("with_published_products", withPublishedProducts)
        logger.add("count", sections.size)
        return ListSectionResponse(
            sections = sections.map { it.toSectionSummary() }
        )
    }

    fun search(accountId: Long, withPublishedProducts: Boolean? = null): List<SectionEntity> {
        val query = em.createQuery(sql(withPublishedProducts))
        parameters(accountId, withPublishedProducts, query)
        return query.resultList as List<SectionEntity>
    }

    private fun sql(withPublishedProducts: Boolean?): String {
        val select = select()
        val where = where(withPublishedProducts)
        return if (where.isNullOrEmpty())
            select
        else
            "$select WHERE $where ORDER BY S.sortOrder"
    }

    private fun select(): String =
        "SELECT S FROM SectionEntity S"

    private fun where(withPublishedProducts: Boolean?): String {
        val criteria = mutableListOf("S.isDeleted=:is_deleted")

        criteria.add("S.accountId=:account_id")
        if (withPublishedProducts == true)
            criteria.add("EXISTS (SELECT P FROM ProductEntity P WHERE P.isDeleted=:is_deleted AND P.status=:status AND P MEMBER OF S.products)")

        return criteria.joinToString(separator = " AND ")
    }

    private fun parameters(accountId: Long, withPublishedProducts: Boolean?, query: Query) {
        query.setParameter("is_deleted", false)
        query.setParameter("account_id", accountId)

        if (withPublishedProducts == true) {
            query.setParameter("status", ProductStatus.PUBLISHED)
        }
    }
}
