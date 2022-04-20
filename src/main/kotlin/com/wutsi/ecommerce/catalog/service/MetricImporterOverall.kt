package com.wutsi.ecommerce.catalog.service

import com.opencsv.CSVReader
import com.wutsi.ecommerce.catalog.dao.PeriodRepository
import com.wutsi.ecommerce.catalog.entity.MetricType
import com.wutsi.ecommerce.catalog.entity.PeriodEntity
import com.wutsi.ecommerce.catalog.entity.PeriodType
import com.wutsi.platform.core.logging.DefaultKVLogger
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.URL
import java.sql.PreparedStatement
import javax.sql.DataSource

@Service
class MetricImporterOverall(
    private val ds: DataSource,
    private val dao: PeriodRepository,
    private val storage: StorageService,
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(MetricImporterOverall::class.java)
        const val SQL = """
            INSERT INTO T_METRIC(period_fk, product_id, merchant_id, type, value)
                VALUES(?, ?, ?, ?, ?)
            ON CONFLICT (period_fk, product_id, type) DO UPDATE SET
                value=?,
                merchant_id=?
        """
    }

    fun import(type: MetricType): Long {
        val logger = DefaultKVLogger()
        logger.add("importer", MetricImporterOverall.javaClass.simpleName)
        logger.add("importer_type", type)

        val cnn = ds.connection
        try {
            val stmt = cnn.prepareStatement(SQL)
            try {
                val imported = import(type, stmt, logger)
                logger.add("importer_count", imported)
                return imported
            } finally {
                stmt.close()
            }
        } finally {
            logger.log()
            cnn.close()
        }
    }

    private fun import(type: MetricType, stmt: PreparedStatement, logger: KVLogger): Long {
        val output = ByteArrayOutputStream()
        load(type, output, logger)

        val reader = InputStreamReader(ByteArrayInputStream(output.toByteArray()))
        reader.use {
            val csv = CSVReader(reader)
            csv.use {
                return import(type, csv, stmt)
            }
        }
    }

    private fun load(type: MetricType, output: ByteArrayOutputStream, logger: KVLogger) {
        val url = toURL(type)
        logger.add("importer_url", url)
        storage.get(url, output)
    }

    private fun import(type: MetricType, csv: CSVReader, stmt: PreparedStatement): Long {
        val mapper = CsvMetricMapper()
        val period = dao.findByTypeAndYearAndMonth(PeriodType.OVERALL, 0, 0)

        var imported = 0L
        var row = 0
        val iterator: Iterator<Array<String>> = csv.iterator()
        while (iterator.hasNext()) {
            val data = iterator.next()
            if (row == 0) {
                mapper.column(data)
            } else {
                // Load the data
                val item = mapper.map(data)
                try {
                    map(type, item, period, stmt)
                    stmt.executeUpdate()
                    imported++
                } catch (ex: Exception) {
                    LOGGER.warn("Unable to import row#$row", ex)
                }
            }
            row++
        }
        return imported
    }

    private fun map(type: MetricType, item: CsvMetric, period: PeriodEntity, stmt: PreparedStatement) {
        stmt.setLong(1, period.id!!)
        stmt.setLong(2, item.productId?.toLong() ?: -1)
        stmt.setLong(3, item.merchantId?.toLong() ?: -1)
        stmt.setInt(4, type.ordinal)
        stmt.setLong(5, item.value)
        stmt.setLong(6, item.value)
        stmt.setLong(7, item.merchantId?.toLong() ?: -1)
    }

    private fun toURL(type: MetricType): URL =
        storage.toURL("/aggregates/overall/" + type.name.lowercase() + ".csv")
}
