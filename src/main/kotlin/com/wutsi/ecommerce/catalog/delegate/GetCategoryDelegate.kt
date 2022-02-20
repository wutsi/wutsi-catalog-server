package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.GetCategoryResponse
import org.springframework.stereotype.Service

@Service
public class GetCategoryDelegate : AbstractProductDelegate() {
    public fun invoke(id: Long): GetCategoryResponse =
        GetCategoryResponse(
            category = getCategory(id).toCategory()
        )
}
