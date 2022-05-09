package com.wutsi.ecommerce.catalog.service.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GoogleCategoryRepositoryTest {
    @Test
    fun findAll() {
        val dao = GoogleCategoryRepository()
        val categories = dao.findAll()

        assertEquals(5595, categories.size)
    }
}
