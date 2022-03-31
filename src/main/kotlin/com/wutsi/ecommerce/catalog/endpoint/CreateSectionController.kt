package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.CreateSectionDelegate
import com.wutsi.ecommerce.catalog.dto.CreateSectionRequest
import com.wutsi.ecommerce.catalog.dto.CreateSectionResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class CreateSectionController(
    private val `delegate`: CreateSectionDelegate
) {
    @PostMapping("/v1/sections")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@Valid @RequestBody request: CreateSectionRequest): CreateSectionResponse =
        delegate.invoke(request)
}
