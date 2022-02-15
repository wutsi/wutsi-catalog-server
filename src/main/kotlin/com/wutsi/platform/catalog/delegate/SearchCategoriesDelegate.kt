package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.SearchCategoryRequest
import com.wutsi.platform.catalog.dto.SearchCategoryResponse
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
