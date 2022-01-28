package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.GetProductDelegate
import com.wutsi.platform.catalog.dto.GetProductResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.GetMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class GetProductController(
    private val `delegate`: GetProductDelegate
) {
    @GetMapping("/v1/products/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    public fun invoke(@PathVariable(name = "id") id: Long): GetProductResponse = delegate.invoke(id)
}
