package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.UpdateCategoryRequest
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UpdateCategoryDelegate(
    dao: CategoryRepository,
    securityManager: SecurityManager,
) : AbstractCategoryDelegate(dao, securityManager) {
    @Transactional
    fun invoke(id: Long, request: UpdateCategoryRequest) {
        val category = getCategory(id)
        category.title = request.title
        dao.save(category)
    }
}
