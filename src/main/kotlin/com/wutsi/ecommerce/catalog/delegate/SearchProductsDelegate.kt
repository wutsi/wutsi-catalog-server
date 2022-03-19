package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.SearchProductRequest
import com.wutsi.ecommerce.catalog.dto.SearchProductResponse
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.ecommerce.catalog.service.SecurityManager
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
        val select = select()
        val where = where(request)
        return if (where.isNullOrEmpty())
            select
        else
            "$select WHERE $where ORDER BY P.id"
    }

    private fun select(): String =
        "SELECT P FROM ProductEntity P"

    private fun where(request: SearchProductRequest): String {
        val criteria = mutableListOf("P.isDeleted=:is_deleted")

        criteria.add("P.tenantId = :tenant_id")
        if (request.accountId != null)
            criteria.add("P.accountId = :account_id")

        if (request.visible != null)
            criteria.add("P.visible = :visible")

        if (request.productIds.isNotEmpty())
            criteria.add("P.id IN :product_ids")

        if (request.categoryIds.isNotEmpty())
            criteria.add("(P.category.id IN :category_ids OR P.subCategory.id IN :category_ids)")

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