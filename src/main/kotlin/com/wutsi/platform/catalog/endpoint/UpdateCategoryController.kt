package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.UpdateCategoryDelegate
import com.wutsi.platform.catalog.dto.UpdateCategoryRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid
import kotlin.Long

@RestController
public class UpdateCategoryController(
    private val `delegate`: UpdateCategoryDelegate
) {
    @PostMapping("/v1/categories/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @Valid @RequestBody
        request: UpdateCategoryRequest
    ) {
        delegate.invoke(id, request)
    }
}
