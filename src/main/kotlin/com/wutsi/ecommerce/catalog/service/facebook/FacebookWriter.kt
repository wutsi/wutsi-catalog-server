package com.wutsi.ecommerce.catalog.service.facebook

import com.opencsv.CSVWriter
import org.springframework.stereotype.Service
import java.io.OutputStream
import java.io.OutputStreamWriter

@Service
class FacebookWriter {
    fun write(items: List<FacebookProduct>, out: OutputStream) {
        val writer = OutputStreamWriter(out)
        val csv = CSVWriter(
            writer,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.DEFAULT_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END
        )
        csv.use {
            headers(csv)
            data(items, csv)
        }
    }

    /**
     * See https://developers.facebook.com/docs/commerce-platform/catalog/fields
     */
    private fun headers(csv: CSVWriter) {
        csv.writeNext(
            arrayOf(
                "id",
                "title",
                "description",
                "availability",
                "condition",
                "price",
                "sale_price",
                "link",
                "image_link",
                "brand",
            )
        )
    }

    private fun data(items: List<FacebookProduct>, csv: CSVWriter) {
        items.forEach { data(it, csv) }
    }

    private fun data(item: FacebookProduct, csv: CSVWriter) {
        csv.writeNext(
            arrayOf(
                string(item.id),
                string(item.title),
                string(item.description),
                string(item.availability),
                string(item.condition),
                string(item.price),
                string(item.salesPrice),
                string(item.link),
                string(item.imageLink),
                string(item.brand),
            )
        )
    }

    private fun string(s: String?) = s ?: ""
}
