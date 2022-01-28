package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.DeletePictureDelegate
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.DeleteMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Long

@RestController
public class DeletePictureController(
    private val `delegate`: DeletePictureDelegate
) {
    @DeleteMapping("/v1/products/pictures/{id}")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@PathVariable(name = "id") id: Long) {
        delegate.invoke(id)
    }
}
