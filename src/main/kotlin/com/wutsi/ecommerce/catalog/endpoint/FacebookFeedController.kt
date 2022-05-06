package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.delegate.FacebookFeedDelegate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FacebookFeedController(
    private val `delegate`: FacebookFeedDelegate
) {
    @GetMapping("/v1/feeds/{merchant-id}/facebook", produces = ["application/csv"])
    fun invoke(
        @PathVariable(name = "merchant-id") merchantId: Long
    ) = delegate.invoke(merchantId)
}
