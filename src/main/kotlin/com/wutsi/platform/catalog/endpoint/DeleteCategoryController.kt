package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.DeleteCategoryDelegate
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.DeleteMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class DeleteCategoryController(
    private val `delegate`: DeleteCategoryDelegate
) {
    @DeleteMapping("/v1/categories/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@PathVariable(name = "id") id: Long) {
        delegate.invoke(id)
    }
}
