package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.AddToSectionDelegate
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class AddToSectionController(
    private val `delegate`: AddToSectionDelegate
) {
    @PostMapping("/v1/sections/{id}/products/{product-id}")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @PathVariable(name = "product-id")
        productId: Long
    ) {
        delegate.invoke(id, productId)
    }
}
