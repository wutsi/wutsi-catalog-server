package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dto.GetCategoryResponse
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
public class GetCategoryDelegate(
    private val request: HttpServletRequest
) : AbstractProductDelegate() {
    public fun invoke(id: Long): GetCategoryResponse =
        GetCategoryResponse(
            category = getCategory(id).toCategory(request)
        )
}
