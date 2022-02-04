package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.GetCategoryDelegate
import com.wutsi.platform.catalog.dto.GetCategoryResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.GetMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class GetCategoryController(
    private val `delegate`: GetCategoryDelegate
) {
    @GetMapping("/v1/categories/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    public fun invoke(@PathVariable(name = "id") id: Long): GetCategoryResponse = delegate.invoke(id)
}
