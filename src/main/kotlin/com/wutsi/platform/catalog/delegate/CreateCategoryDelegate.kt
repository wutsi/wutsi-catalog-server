package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.CreateCategoryRequest
import com.wutsi.platform.catalog.dto.CreateCategoryResponse
import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
class CreateCategoryDelegate(
    private val dao: CategoryRepository,
    private val securityManager: SecurityManager,
) {
    @Transactional
    fun invoke(request: CreateCategoryRequest): CreateCategoryResponse {
        val now = OffsetDateTime.now()
        val category = dao.save(
            CategoryEntity(
                title = request.title,
                accountId = securityManager.accountId(),
                tenantId = securityManager.tenantId(),
                created = now,
                updated = now
            )
        )
        return CreateCategoryResponse(
            id = category.id ?: -1
        )
    }
}
