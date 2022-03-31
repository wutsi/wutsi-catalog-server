package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.GetSectionDelegate
import com.wutsi.ecommerce.catalog.dto.GetSectionResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.GetMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class GetSectionController(
    private val `delegate`: GetSectionDelegate
) {
    @GetMapping("/v1/sections/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    public fun invoke(@PathVariable(name = "id") id: Long): GetSectionResponse = delegate.invoke(id)
}
