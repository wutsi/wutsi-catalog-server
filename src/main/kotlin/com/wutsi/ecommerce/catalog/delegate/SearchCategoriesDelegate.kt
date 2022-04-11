package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.CategoryRepository
import com.wutsi.ecommerce.catalog.dto.SearchCategoryRequest
import com.wutsi.ecommerce.catalog.dto.SearchCategoryResponse
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.stereotype.Service

@Service
class SearchCategoriesDelegate(
    private val dao: CategoryRepository,
    private val logger: KVLogger
) {
    fun invoke(request: SearchCategoryRequest): SearchCategoryResponse {
        val categories = dao.findByParentId(request.parentId)

        logger.add("parent_id", request.parentId)
        logger.add("count", categories.size)
        return SearchCategoryResponse(
            categories = categories.map { it.toCategorySummary() }
        )
    }
}
