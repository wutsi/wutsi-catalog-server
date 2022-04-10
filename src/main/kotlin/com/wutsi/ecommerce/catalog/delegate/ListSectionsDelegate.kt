package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dto.ListSectionResponse
import org.springframework.stereotype.Service

@Service
class ListSectionsDelegate : AbstractSectionDelegate() {
    fun invoke(accountId: Long) = ListSectionResponse(
        sections = dao.findByAccountId(accountId)
            .filter { !it.isDeleted }
            .sortedBy { it.sortOrder }
            .map { it.toSectionSummary() }
    )
}
