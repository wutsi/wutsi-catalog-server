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
        assertEquals(469, matcher.find("Health & beauty", categories)?.id)
        assertEquals(4057, matcher.find("Hair Extensions", categories)?.id)
        assertEquals(222, matcher.find("Electronics", categories)?.id)
        assertEquals(536, matcher.find("Home & Garden", categories)?.id)
        assertEquals(1876, matcher.find("Bakery", categories)?.id)

        assertEquals(278, matcher.find("Computers", categories)?.id)
        assertEquals(328, matcher.find("Laptops", categories)?.id)
        assertEquals(4745, matcher.find("Tablet Computers", categories)?.id)
        assertEquals(325, matcher.find("Desktop Computers", categories)?.id)
        assertEquals(331, matcher.find("Computer Servers", categories)?.id)

        assertEquals(988, matcher.find("Sport", categories)?.id)
        assertEquals(422, matcher.find("Food Items", categories)?.id) // Update Name
        assertEquals(166, matcher.find("Apparel & Accessories", categories)?.id)
        assertEquals(413, matcher.find("Beverages", categories)?.id)
        assertEquals(267, matcher.find("Mobile Phones", categories)?.id) // Rename to 'Mobile Phones & Accessories'
        assertEquals(784, matcher.find("Books", categories)?.id)
    }
}
