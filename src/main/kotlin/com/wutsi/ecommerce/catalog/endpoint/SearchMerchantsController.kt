package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.`delegate`.SearchMerchantsDelegate
import com.wutsi.ecommerce.catalog.dto.SearchMerchantRequest
import com.wutsi.ecommerce.catalog.dto.SearchMerchantResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class SearchMerchantsController(
    private val `delegate`: SearchMerchantsDelegate
) {
    @PostMapping("/v1/merchants/search")
    @PreAuthorize(value = "hasAuthority('catalog-read')")
    public fun invoke(@Valid @RequestBody request: SearchMerchantRequest): SearchMerchantResponse =
        delegate.invoke(request)
}
