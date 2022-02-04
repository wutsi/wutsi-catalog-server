package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.delegate.SearchCategoriesDelegate
import com.wutsi.platform.catalog.dto.SearchCategoryRequest
import com.wutsi.platform.catalog.dto.SearchCategoryResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
public class SearchCategoriesController(
    private val `delegate`: SearchCategoriesDelegate
) {
    @PostMapping("/v1/categories/search")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@Valid @RequestBody request: SearchCategoryRequest): SearchCategoryResponse =
        delegate.invoke(request)
}
