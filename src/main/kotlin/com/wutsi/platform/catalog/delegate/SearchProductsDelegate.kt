package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dto.SearchProductRequest
import com.wutsi.platform.catalog.dto.SearchProductResponse
import com.wutsi.platform.catalog.entity.ProductEntity
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.Query

@Service
public class SearchProductsDelegate(
    private val securityManager: SecurityManager,
    private val em: EntityManager,
) {
    public fun invoke(request: SearchProductRequest): SearchProductResponse {
        val query = em.createQuery(sql(request))
        parameters(request, query)
        val products = query
            .setFirstResult(request.offset)
            .setMaxResults(request.limit)
            .resultList as List<ProductEntity>

        return SearchProductResponse(
            products = products.map { it.toProductSummary() }
        )
    }

    private fun sql(request: SearchProductRequest): String {
        val select = select(request)
        val where = where(request)
        return if (where.isNullOrEmpty())
            select
        else
            "$select WHERE $where ORDER BY a.id"
    }

    private fun select(request: SearchProductRequest): String =
        if (request.categoryIds.isEmpty())
            "SELECT a FROM ProductEntity a"
        else
            "SELECT a FROM ProductEntity a JOIN a.categories c"

    private fun where(request: SearchProductRequest): String {
        val criteria = mutableListOf("a.isDeleted=:is_deleted")

        criteria.add("a.tenantId = :tenant_id")
        if (request.accountId != null)
            criteria.add("a.accountId = :account_id")

        if (request.visible != null)
            criteria.add("a.visible = :visible")

        if (request.productIds.isNotEmpty())
            criteria.add("a.id IN :product_ids")

        if (request.categoryIds.isNotEmpty())
            criteria.add("c.id IN :category_ids")

        return criteria.joinToString(separator = " AND ")
    }

    private fun parameters(request: SearchProductRequest, query: Query) {
        query.setParameter("is_deleted", false)
        query.setParameter("tenant_id", securityManager.tenantId())

        if (request.accountId != null)
            query.setParameter("account_id", request.accountId)

        if (request.visible != null)
            query.setParameter("visible", request.visible)

        if (request.productIds.isNotEmpty())
            query.setParameter("product_ids", request.productIds)

        if (request.categoryIds.isNotEmpty())
            query.setParameter("category_ids", request.categoryIds)
    }
}
