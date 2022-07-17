package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.dto.CreateSectionRequest
import com.wutsi.ecommerce.catalog.dto.CreateSectionResponse
import com.wutsi.ecommerce.catalog.entity.SectionEntity
import com.wutsi.ecommerce.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CreateSectionDelegate(
    private val dao: SectionRepository,
    private val securityManager: SecurityManager
) {
    @Transactional
    fun invoke(request: CreateSectionRequest): CreateSectionResponse {
        val accountId = securityManager.accountId()
        val sortOrder = lastSortOrder(accountId)

        val section = dao.save(
            SectionEntity(
                accountId = accountId,
                tenantId = securityManager.tenantId()!!,
                title = request.title,
                sortOrder = sortOrder + 1,
            )
        )
        return CreateSectionResponse(id = section.id ?: -1)
    }

    private fun lastSortOrder(accountId: Long): Int {
        val sections = dao.findByAccountId(accountId)
            .filter { !it.isDeleted }
            .sortedByDescending { it.sortOrder }
        return if (sections.isEmpty()) 0 else sections[0].sortOrder
    }
}
