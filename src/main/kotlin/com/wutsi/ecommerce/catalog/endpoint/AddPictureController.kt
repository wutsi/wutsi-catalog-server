package com.wutsi.ecommerce.catalog.endpoint

import com.wutsi.ecommerce.catalog.delegate.AddPictureDelegate
import com.wutsi.ecommerce.catalog.dto.AddPictureRequest
import com.wutsi.ecommerce.catalog.dto.AddPictureResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
public class AddPictureController(
    private val `delegate`: AddPictureDelegate
) {
    @PostMapping("/v1/products/{id}/pictures")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(
        @PathVariable(name = "id") id: Long,
        @Valid @RequestBody
        request: AddPictureRequest
    ): AddPictureResponse = delegate.invoke(id, request)
}
