package com.wutsi.ecommerce.catalog.`delegate`

import com.wutsi.ecommerce.catalog.dao.MerchantRepository
import com.wutsi.ecommerce.catalog.dto.GetMerchantResponse
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.ecommerce.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GetMerchantDelegate(
    private val dao: MerchantRepository,
    private val securityManager: SecurityManager,
) {
    fun invoke(id: Long): GetMerchantResponse {
        val merchant = dao.findById(id)
            .orElseThrow {
                notFound(id, ErrorURN.MERCHANT_NOT_FOUND)
            }
        if (!merchant.isEnabled)
            throw notFound(id, ErrorURN.MERCHANT_DISABLED)

        securityManager.checkTenant(merchant)

        return GetMerchantResponse(
            merchant = merchant.toMerchant()
        )
    }

    private fun notFound(id: Long, error: ErrorURN) = NotFoundException(
        error = Error(
            code = error.urn,
            parameter = Parameter(
                name = "id",
                value = id,
                type = ParameterType.PARAMETER_TYPE_PATH
            )
        )
    )
}
