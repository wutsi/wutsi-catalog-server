package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.SearchProductsDelegate
import com.wutsi.ecommerce.catalog.dto.SearchProductRequest
import com.wutsi.ecommerce.catalog.dto.SearchProductResponse
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
