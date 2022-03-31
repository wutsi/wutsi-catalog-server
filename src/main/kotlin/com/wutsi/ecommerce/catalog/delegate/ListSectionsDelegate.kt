package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dto.ListSectionResponse
import org.springframework.stereotype.Service

@Service
class ListSectionsDelegate : AbstractSectionDelegate() {
    fun invoke() = ListSectionResponse(
        sections = dao.findByAccountId(securityManager.accountId())
            .filter { !it.isDeleted }
            .sortedBy { it.sortOrder }
            .map { it.toSectionSummary() }
    )
}
