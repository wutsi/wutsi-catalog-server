package com.wutsi.ecommerce.catalog.service.google

import kotlin.math.min


class GoogleCategoryMatcher {
    fun find(text: String, categories: List<GoogleCategory>): GoogleCategory? =
        findLastCategory(text, categories) ?: findContains(text, categories)


    private fun findLastCategory(text: String, categories: List<GoogleCategory>): GoogleCategory? =
        categories.filter { it.name.endsWith(" > $text", ignoreCase = true) }
            .sortedBy { it.name.split(">").size }
            .firstOrNull()

//    private fun findEndsWith(text: String, categories: List<GoogleCategory>): GoogleCategory? =
//        categories.filter { it.name.endsWith(text, ignoreCase = true) }
//            .sortedBy { it.name.split(">").size }
//            .firstOrNull()

    private fun findContains(text: String, categories: List<GoogleCategory>): GoogleCategory? =
        categories.filter { it.name.contains(text, ignoreCase = true) }
//            .sortedBy { it.name.split(">").size }
            .sortedBy { levenshtein(text, it.name) }
            .firstOrNull()

    fun levenshtein(lhs: CharSequence, rhs: CharSequence): Int {
        if (lhs == rhs) {
            return 0
        }
        if (lhs.isEmpty()) {
            return rhs.length
        }
        if (rhs.isEmpty()) {
            return lhs.length
        }

        val lhsLength = lhs.length + 1
        val rhsLength = rhs.length + 1

        var cost = Array(lhsLength) { it }
        var newCost = Array(lhsLength) { 0 }

        for (i in 1..rhsLength - 1) {
            newCost[0] = i

            for (j in 1..lhsLength - 1) {
                val match = if (lhs[j - 1] == rhs[i - 1]) 0 else 1

                val costReplace = cost[j - 1] + match
                val costInsert = cost[j] + 1
                val costDelete = newCost[j - 1] + 1

                newCost[j] = min(min(costInsert, costDelete), costReplace)
            }

            val swap = cost
            cost = newCost
            newCost = swap
        }

        return cost[lhsLength - 1]
    }
}
