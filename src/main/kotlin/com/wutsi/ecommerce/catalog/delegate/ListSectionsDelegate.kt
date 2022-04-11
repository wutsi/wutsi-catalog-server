package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dto.ListSectionResponse
import com.wutsi.platform.core.logging.KVLogger
import org.springframework.stereotype.Service

@Service
class ListSectionsDelegate(private val logger: KVLogger) : AbstractSectionDelegate() {
    fun invoke(accountId: Long): ListSectionResponse {
        val sections = dao.findByAccountId(accountId)
            .filter { !it.isDeleted }
            .sortedBy { it.sortOrder }

        logger.add("count", sections.size)
        return ListSectionResponse(
            sections = sections.map { it.toSectionSummary() }
        )
    }
}
