package com.wutsi.ecommerce.catalog.service.google

import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStreamReader


class GoogleCategoryRepository {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(GoogleCategoryRepository::class.java)
    }

    fun findAll(): List<GoogleCategory> {
        val categories = mutableListOf<GoogleCategory>()
        val input = javaClass.getResourceAsStream("/google/google-taxonomy-with-ids.en-US.txt")
        val reader = BufferedReader(InputStreamReader(input))
        var lineNumber = 0
        reader.use {
            while (reader.ready()) {
                lineNumber++
                val line = reader.readLine().trim()
                if (line.startsWith("#"))
                    continue
                val i = line.indexOf('-')
                if (i > 0) {
                    categories.add(
                        GoogleCategory(
                            id = line.substring(0, i).trim().toLong(),
                            name = line.substring(i + 1).trim()
                        )
                    )
                } else {
                    LOGGER.info("Invalid line - $lineNumber: $line")
                }
            }
        }
        return categories
    }
}
