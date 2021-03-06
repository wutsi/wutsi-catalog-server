package com.wutsi.ecommerce.catalog.service.metrics

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.logging.DefaultKVLogger
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.URL
import java.sql.PreparedStatement
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.sql.DataSource

abstract class AbstractMetricImporter(
    protected val ds: DataSource,
    protected val storage: StorageService,
) {
    protected abstract fun sql(type: MetricType): String
    protected abstract fun map(item: CsvMetric, stmt: PreparedStatement)

    open fun import(date: LocalDate, type: MetricType): Long {
        val cnn = ds.connection
        val logger = DefaultKVLogger()
        logger.add("importer", javaClass.simpleName)
        logger.add("date", date)
        logger.add("type", type)

        try {
            val stmt = cnn.prepareStatement(sql(type))
            try {
                val imported = import(date, type, stmt, logger)

                logger.add("count", imported)
                logger.add("success", true)
                return imported
            } finally {
                stmt.close()
            }
        } catch (ex: Exception) {
            logger.add("success", false)
            logger.setException(ex)
            return 0
        } finally {
            logger.log()
            cnn.close()
        }
    }

    private fun import(date: LocalDate, type: MetricType, stmt: PreparedStatement, logger: KVLogger): Long {
        val output = ByteArrayOutputStream()
        load(date, type, output, logger)

        val reader = InputStreamReader(ByteArrayInputStream(output.toByteArray()))
        reader.use {
            val csv = CSVReader(reader)
            csv.use {
                return import(csv, stmt)
            }
        }
    }

    private fun load(date: LocalDate, type: MetricType, output: ByteArrayOutputStream, logger: KVLogger) {
        val url = toURL(date, type)
        logger.add("url", url)
        storage.get(url, output)
    }

    private fun import(csv: CSVReader, stmt: PreparedStatement): Long {
        val mapper = CsvMetricMapper()

        var row = 0
        val iterator: Iterator<Array<String>> = csv.iterator()
        val keys = mutableSetOf<String>()
        while (iterator.hasNext()) {
            val data = iterator.next()
            if (row == 0) {
                mapper.column(data)
            } else {
                // Load the data
                val item = mapper.map(data)
                try {
                    val key = toKey(item)
                    if (keys.add(key)) {
                        map(item, stmt)
                        stmt.executeUpdate()
                    }
                } catch (ex: Exception) {
                    LoggerFactory.getLogger(javaClass).warn("Unable to import row#$row", ex)
                }
            }
            row++
        }
        return keys.size.toLong()
    }

    protected open fun toURL(date: LocalDate, type: MetricType): URL =
        storage.toURL(
            "aggregates/daily/" +
                date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd/")) +
                type.name.lowercase() + ".csv"
        )

    protected open fun toKey(item: CsvMetric): String =
        UUID.randomUUID().toString()
}
