package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
public class DeleteCategoryDelegate(
    dao: CategoryRepository,
    securityManager: SecurityManager,
) : AbstractCategoryDelegate(dao, securityManager) {
    @Transactional
    public fun invoke(id: Long) {
        val category = getCategory(id)
        category.deleted = OffsetDateTime.now()
        category.isDeleted = true
        dao.save(category)
    }
}
