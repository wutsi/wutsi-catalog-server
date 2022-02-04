package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.UpdateCategoryAttributeDelegate
import com.wutsi.platform.catalog.dto.UpdateCategoryAttributeRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid
import kotlin.Long
import kotlin.String

@RestController
public class UpdateCategoryAttributeController(
    private val `delegate`: UpdateCategoryAttributeDelegate
) {
    @PostMapping("/v1/categories/{id}/attributes/{name}")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @PathVariable(name = "name") name: String,
        @Valid @RequestBody request: UpdateCategoryAttributeRequest
    ) {
        delegate.invoke(id, name, request)
    }
}
