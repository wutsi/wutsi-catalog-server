package com.wutsi.ecommerce.catalog.dao

import com.wutsi.ecommerce.catalog.entity.MerchantEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MerchantRepository : CrudRepository<MerchantEntity, Long> {
    fun findByAccountId(accountId: Long): Optional<MerchantEntity>
}
