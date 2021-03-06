package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.CreateProductDelegate
import com.wutsi.ecommerce.catalog.dto.CreateProductRequest
import com.wutsi.ecommerce.catalog.dto.CreateProductResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class CreateProductController(
    private val `delegate`: CreateProductDelegate
) {
    @PostMapping("/v1/products")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@Valid @RequestBody request: CreateProductRequest): CreateProductResponse =
        delegate.invoke(request)
}
