package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.SearchProductsDelegate
import com.wutsi.platform.catalog.dto.SearchProductRequest
import com.wutsi.platform.catalog.dto.SearchProductResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class SearchProductsController(
    private val `delegate`: SearchProductsDelegate
) {
    @PostMapping("/v1/products/search")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    public fun invoke(@Valid @RequestBody request: SearchProductRequest): SearchProductResponse =
        delegate.invoke(request)
}
