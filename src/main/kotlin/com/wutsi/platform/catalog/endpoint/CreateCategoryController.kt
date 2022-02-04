package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.CreateCategoryDelegate
import com.wutsi.platform.catalog.dto.CreateCategoryRequest
import com.wutsi.platform.catalog.dto.CreateCategoryResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class CreateCategoryController(
    private val `delegate`: CreateCategoryDelegate
) {
    @PostMapping("/v1/categories")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@Valid @RequestBody request: CreateCategoryRequest): CreateCategoryResponse =
        delegate.invoke(request)
}
