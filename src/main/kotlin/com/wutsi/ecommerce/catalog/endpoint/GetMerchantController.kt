package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.GetMerchantDelegate
import com.wutsi.ecommerce.catalog.dto.GetMerchantResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.GetMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class GetMerchantController(
    private val `delegate`: GetMerchantDelegate
) {
    @GetMapping("/v1/merchants/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    public fun invoke(@PathVariable(name = "id") id: Long): GetMerchantResponse = delegate.invoke(id)
}
