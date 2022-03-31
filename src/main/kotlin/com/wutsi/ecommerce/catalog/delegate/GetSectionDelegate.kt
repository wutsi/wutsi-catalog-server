package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dto.GetSectionResponse
import org.springframework.stereotype.Service

@Service
class GetSectionDelegate : AbstractSectionDelegate() {
    fun invoke(id: Long) = GetSectionResponse(
        section = findById(id).toSection()
    )
}
