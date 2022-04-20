package com.wutsi.ecommerce.catalog.service

import java.util.Locale

abstract class AbstractCsvMapper<T> {
    private val columnIndex: MutableMap<String, Int> = HashMap()

    fun column(col: Array<String>) {
        for (i in col.indices) {
            columnIndex[col[i].lowercase(Locale.getDefault())] = i
        }
    }

    abstract fun map(col: Array<String>): T

    protected fun getString(column: String, col: Array<String>): String? {
        val index = columnIndex[column.lowercase(Locale.getDefault())] ?: return null
        return if (index < col.size) col[index] else null
    }

    protected fun getDouble(column: String, col: Array<String>): Double? =
        try {
            getString(column, col)?.toDouble()
        } catch (ex: Exception) {
            null
        }

    protected fun getLong(column: String, col: Array<String>): Long? =
        try {
            getString(column, col)?.toLong()
        } catch (ex: Exception) {
            null
        }
}
