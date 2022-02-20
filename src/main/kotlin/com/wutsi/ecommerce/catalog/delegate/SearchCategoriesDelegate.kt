package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.CategoryRepository
import com.wutsi.ecommerce.catalog.dto.SearchCategoryRequest
import com.wutsi.ecommerce.catalog.dto.SearchCategoryResponse
import org.springframework.stereotype.Service

@Service
class SearchCategoriesDelegate(
    private val dao: CategoryRepository,
) {
    fun invoke(request: SearchCategoryRequest): SearchCategoryResponse =
        SearchCategoryResponse(
            categories = dao.findByParentId(request.parentId)
                .map { it.toCategorySummary() }
        )
}
