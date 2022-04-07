package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.UnpublishProductDelegate
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.DeleteMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class UnpublishProductController(
    private val `delegate`: UnpublishProductDelegate
) {
    @DeleteMapping("/v1/products/{id}/publish")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@PathVariable(name = "id") id: Long) {
        delegate.invoke(id)
    }
}
