package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.RemoveCategoryDelegate
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.DeleteMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class RemoveCategoryController(
    private val `delegate`: RemoveCategoryDelegate
) {
    @DeleteMapping("/v1/products/{id}/categories/{category-id}")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @PathVariable(name = "category-id")
        categoryId: Long
    ) {
        delegate.invoke(id, categoryId)
    }
}
