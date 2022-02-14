package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.SearchCategoryRequest
import com.wutsi.platform.catalog.dto.SearchCategoryResponse
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class SearchCategoriesDelegate(
    private val dao: CategoryRepository,
    private val httpRequest: HttpServletRequest,
) {
    fun invoke(request: SearchCategoryRequest): SearchCategoryResponse =
        SearchCategoryResponse(
            categories = dao.findByParentId(request.parentId)
                .map { it.toCategorySummary(httpRequest) }
        )
}
