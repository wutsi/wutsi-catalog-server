package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dao.SectionRepository
import com.wutsi.ecommerce.catalog.entity.SectionEntity
import com.wutsi.ecommerce.catalog.error.ErrorURN
import com.wutsi.ecommerce.catalog.service.SecurityManager
import com.wutsi.platform.core.error.Error
import com.wutsi.platform.core.error.Parameter
import com.wutsi.platform.core.error.ParameterType
import com.wutsi.platform.core.error.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AbstractSectionDelegate {
    @Autowired
    protected lateinit var dao: SectionRepository

    @Autowired
    protected lateinit var securityManager: SecurityManager

    @Transactional
    fun findById(id: Long): SectionEntity {
        val section = dao.findById(id)
            .orElseThrow {
                notFound(id)
            }

        if (section.isDeleted)
            throw notFound(id)

        return section
    }

    private fun notFound(id: Long) = NotFoundException(
        error = Error(
            code = ErrorURN.SECTION_NOT_FOUND.urn,
            parameter = Parameter(
                name = "id",
                type = ParameterType.PARAMETER_TYPE_PATH,
                value = id
            )
        )
    )
}
