package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dto.GetCategoryResponse
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service

@Service
public class GetCategoryDelegate(
    dao: CategoryRepository,
    securityManager: SecurityManager
) : AbstractCategoryDelegate(dao, securityManager) {
    public fun invoke(id: Long): GetCategoryResponse =
        GetCategoryResponse(
            category = getCategory(id).toCategory()
        )
}
