package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.SearchProductRequest
import com.wutsi.ecommerce.catalog.dto.SearchProductResponse
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.ecommerce.catalog.entity.ProductSort
import com.wutsi.ecommerce.catalog.entity.ProductStatus
import com.wutsi.ecommerce.catalog.entity.ProductType
import com.wutsi.ecommerce.catalog.service.SecurityManager
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.tenant.WutsiTenantApi
import com.wutsi.platform.tenant.dto.Tenant
import com.wutsi.platform.tenant.entity.ToggleName
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.Query

@Service
class SearchProductsDelegate(
    private val securityManager: SecurityManager,
    private val tenantApi: WutsiTenantApi,
    private val em: EntityManager,
    private val logger: KVLogger
) {
    fun invoke(request: SearchProductRequest): SearchProductResponse {
        logger.add("offset", request.offset)
        logger.add("limit", request.limit)
        logger.add("sort_by", request.sortBy)
        logger.add("status", request.status)
        logger.add("section_id", request.sectionId)
        logger.add("product_ids", request.productIds)
        logger.add("category_ids", request.categoryIds)
        logger.add("account_id", request.accountId)
        logger.add("visible", request.visible)

        val products = search(request, securityManager.tenantId())
        logger.add("count", products.size)
        return SearchProductResponse(
            products = products.map { it.toProductSummary() }
        )
    }

    fun search(request: SearchProductRequest, tenantId: Long?): List<ProductEntity> {
        val query = em.createQuery(sql(request, tenantId))
        parameters(request, query, tenantId)
        val products = query
            .setFirstResult(request.offset)
            .setMaxResults(request.limit)
            .resultList as List<ProductEntity>

        return filterOutUnsupportedProducts(products)
    }

    protected fun filterOutUnsupportedProducts(products: List<ProductEntity>): List<ProductEntity> {
        if (products.isEmpty())
            return emptyList()

        val tenant = tenantApi.getTenant(products[0].tenantId).tenant

        var result = products
        if (!supportsDigitalProducts(tenant))
            result = products.filter { it.type != ProductType.NUMERIC }

        return result
    }

    private fun supportsDigitalProducts(tenant: Tenant): Boolean =
        tenant.toggles.find { it.name == ToggleName.STORE_DIGITAL_PRODUCT.name } != null

    private fun sql(request: SearchProductRequest, tenantId: Long?): String {
        val select = select(request)
        val where = where(request, tenantId)
        val orderBy = orderBy(request)
        return if (where.isNullOrEmpty())
            select
        else
            "$select WHERE $where ORDER BY $orderBy"
    }

    private fun select(request: SearchProductRequest): String =
        if (request.sectionId == null)
            "SELECT P FROM ProductEntity P"
        else
            "SELECT P FROM ProductEntity P JOIN P.sections s"

    private fun where(request: SearchProductRequest, tenantId: Long?): String {
        val criteria = mutableListOf("P.isDeleted=:is_deleted")

        if (tenantId != null)
            criteria.add("P.tenantId = :tenant_id")

        if (request.accountId != null)
            criteria.add("P.accountId = :account_id")

        if (request.visible != null)
            criteria.add("P.visible = :visible")

        if (request.productIds.isNotEmpty())
            criteria.add("P.id IN :product_ids")

        if (request.categoryIds.isNotEmpty())
            criteria.add("(P.category.id IN :category_ids OR P.subCategory.id IN :category_ids)")

        if (request.sectionId != null)
            criteria.add("s.id=:section_id")

        if (!request.status.isNullOrEmpty())
            criteria.add("P.status=:status")

        // Ensure that the merchant is enabled!!
        criteria.add("EXISTS (SELECT M FROM MerchantEntity M WHERE M.accountId=P.accountId AND M.isEnabled=true)")

        return criteria.joinToString(separator = " AND ")
    }

    private fun orderBy(request: SearchProductRequest): String =
        if (ProductSort.PRICE_DESC.name.equals(request.sortBy, true))
            "P.price DESC, P.score DESC" // Price, then score
        else if (ProductSort.PRICE_ASC.name.equals(request.sortBy, true))
            "P.price ASC, P.score DESC" // Price, then score
        else if (ProductSort.VIEWS.name.equals(request.sortBy, true))
            "P.totalViews DESC, P.score DESC" // Views, then score
        else if (ProductSort.TITLE.name.equals(request.sortBy, true))
            "P.title"
        else
            "P.score DESC, P.totalViews DESC" // Views, then views

    private fun parameters(request: SearchProductRequest, query: Query, tenantId: Long?) {
        query.setParameter("is_deleted", false)

        if (tenantId != null)
            query.setParameter("tenant_id", tenantId)

        if (request.accountId != null)
            query.setParameter("account_id", request.accountId)

        if (request.visible != null)
            query.setParameter("visible", request.visible)

        if (request.productIds.isNotEmpty())
            query.setParameter("product_ids", request.productIds)

        if (request.categoryIds.isNotEmpty())
            query.setParameter("category_ids", request.categoryIds)

        if (request.sectionId != null)
            query.setParameter("section_id", request.sectionId)

        if (!request.status.isNullOrEmpty())
            query.setParameter("status", ProductStatus.valueOf(request.status.uppercase()))
    }
}
