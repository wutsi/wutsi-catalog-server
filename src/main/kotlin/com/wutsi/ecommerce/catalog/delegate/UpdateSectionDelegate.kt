package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dto.UpdateSectionRequest
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
class UpdateSectionDelegate : AbstractSectionDelegate() {
    @Transactional
    fun invoke(id: Long, request: UpdateSectionRequest) {
        val section = findById(id)
        securityManager.checkOwnership(section)

        section.title = request.title
        section.sortOrder = request.sortOrder
        section.updated = OffsetDateTime.now()
        dao.save(section)
    }
}
