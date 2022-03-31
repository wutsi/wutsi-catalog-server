package com.wutsi.ecommerce.catalog.`delegate`

import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
class DeleteSectionDelegate : AbstractSectionDelegate() {
    @Transactional
    fun invoke(id: Long) {
        val section = findById(id)
        securityManager.checkOwnership(section)

        section.isDeleted = true
        section.deleted = OffsetDateTime.now()
        dao.save(section)
    }
}
