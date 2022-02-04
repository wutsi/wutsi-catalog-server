package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.AddCategoryDelegate
import com.wutsi.platform.catalog.dto.AddCategoryRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid
import kotlin.Long

@RestController
public class AddCategoryController(
    private val `delegate`: AddCategoryDelegate
) {
    @PostMapping("/v1/products/{id}/categories")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @Valid @RequestBody
        request: AddCategoryRequest
    ) {
        delegate.invoke(id, request)
    }
}
