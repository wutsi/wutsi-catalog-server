package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.SearchCategoryRequest
import com.wutsi.platform.catalog.dto.SearchCategoryResponse
import org.springframework.stereotype.Service

@Service
public class SearchCategoriesDelegate(
    private val dao: CategoryRepository,
) {
    public fun invoke(request: SearchCategoryRequest): SearchCategoryResponse =
        SearchCategoryResponse(
            categories = dao.findByAccountId(request.accountId)
                .filter { !it.isDeleted }
                .map { it.toCategorySummary() }
        )
}
