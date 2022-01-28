package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.UpdateProductAttributeDelegate
import com.wutsi.platform.catalog.dto.UpdateProductAttributeRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid
import kotlin.Long
import kotlin.String

@RestController
public class UpdateProductAttributeController(
    private val `delegate`: UpdateProductAttributeDelegate
) {
    @PostMapping("/v1/products/{id}/attributes/{name}")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @PathVariable(name = "name") name: String,
        @Valid @RequestBody request: UpdateProductAttributeRequest
    ) {
        delegate.invoke(id, name, request)
    }
}
