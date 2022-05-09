package com.wutsi.ecommerce.catalog.service.google

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GoogleCategoryMatcherTest {
    val matcher = GoogleCategoryMatcher()

    @Test
    fun matches() {
        // GIVEN
        val categories = GoogleCategoryRepository().findAll()

        // THEN
        assertEquals(249, matcher.find("Speakers", categories)?.id)
        assertEquals(469, matcher.find("beauty", categories)?.id)
        assertEquals(4057, matcher.find("Hair Extensions", categories)?.id)
        assertEquals(222, matcher.find("Electronics", categories)?.id)
        assertEquals(536, matcher.find("Home", categories)?.id)
        assertEquals(4057, matcher.find("Bakery", categories)?.id)
        assertEquals(4057, matcher.find("Computers", categories)?.id)
        assertEquals(4057, matcher.find("Sport", categories)?.id)
        assertEquals(4057, matcher.find("Food", categories)?.id)
        assertEquals(-1, matcher.find("Fashion", categories)?.id)
        assertEquals(413, matcher.find("Beverages", categories)?.id)
        assertEquals(-1, matcher.find("Cell Phones & Accessories", categories)?.id)
        assertEquals(784, matcher.find("Books", categories)?.id)
    }
}
