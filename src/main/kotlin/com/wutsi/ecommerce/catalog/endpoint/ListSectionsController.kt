package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.delegate.ListSectionsDelegate
import com.wutsi.ecommerce.catalog.dto.ListSectionResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ListSectionsController(
    private val `delegate`: ListSectionsDelegate
) {
    @GetMapping("/v1/sections")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    fun invoke(
        @RequestParam(name = "account-id") accountId: Long,
        @RequestParam(name = "with-published-products", required = false) withPublishedProducts: Boolean? =
            null
    ): ListSectionResponse = delegate.invoke(accountId, withPublishedProducts)
}
