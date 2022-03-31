package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.UpdateSectionDelegate
import com.wutsi.ecommerce.catalog.dto.UpdateSectionRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid
import kotlin.Long

@RestController
public class UpdateSectionController(
    private val `delegate`: UpdateSectionDelegate
) {
    @PostMapping("/v1/sections/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @Valid @RequestBody
        request: UpdateSectionRequest
    ) {
        delegate.invoke(id, request)
    }
}
