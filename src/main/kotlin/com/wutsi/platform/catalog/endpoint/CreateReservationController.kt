package com.wutsi.platform.catalog.endpoint

import com.wutsi.platform.catalog.`delegate`.CreateReservationDelegate
import com.wutsi.platform.catalog.dto.CreateReservationRequest
import com.wutsi.platform.catalog.dto.CreateReservationResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class CreateReservationController(
    private val `delegate`: CreateReservationDelegate
) {
    @PostMapping("/v1/reservations")
    @PreAuthorize(value = "hasAuthority('catalog-manage')")
    public fun invoke(@Valid @RequestBody request: CreateReservationRequest):
        CreateReservationResponse = delegate.invoke(request)
}
