package com.wutsi.platform.catalog.`delegate`

import com.wutsi.platform.catalog.dao.CategoryRepository
import com.wutsi.platform.catalog.dao.ProductRepository
import com.wutsi.platform.catalog.service.SecurityManager
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
public class RemoveCategoryDelegate(
    dao: ProductRepository,
    securityManager: SecurityManager,
    private val categoryDao: CategoryRepository
) : AbstractProductDelegate(dao, securityManager) {
    @Transactional
    public fun invoke(id: Long, categoryId: Long) {
        val product = getProduct(id)
        val category = categoryDao.findById(categoryId).get()
        if (product.categories.remove(category))
            dao.save(product)
    }
}
