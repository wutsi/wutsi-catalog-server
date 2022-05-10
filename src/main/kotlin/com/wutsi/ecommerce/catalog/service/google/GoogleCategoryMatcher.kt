package com.wutsi.ecommerce.catalog.service.google

import org.springframework.stereotype.Service

@Service
class GoogleCategoryMatcher {
    fun find(text: String, categories: List<GoogleCategory>): GoogleCategory? =
        findLastCategory(text, categories) ?: findContains(text, categories)

    private fun findLastCategory(text: String, categories: List<GoogleCategory>): GoogleCategory? =
        categories.filter { it.name.equals(text, true) || it.name.endsWith(" > $text", ignoreCase = true) }
            .sortedBy { it.name.split(">").size }
            .firstOrNull()

    private fun findContains(text: String, categories: List<GoogleCategory>): GoogleCategory? =
        categories.filter { it.name.contains(text, ignoreCase = true) }
            .sortedBy { it.name.split(">").size }
            .firstOrNull()
}
