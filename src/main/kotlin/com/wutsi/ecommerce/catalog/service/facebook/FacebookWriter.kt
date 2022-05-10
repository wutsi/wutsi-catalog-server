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
     * See https://developers.facebook.com/docs/marketing-api/catalog/reference/
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
                "brand",
                "google_product_category",
                "link",
                "image_link",
                "additional_image_link"
            )
        )
    }

    private fun data(items: List<FacebookProduct>, csv: CSVWriter) {
        items.forEach { data(it, csv) }
    }

    private fun data(item: FacebookProduct, csv: CSVWriter) {
        csv.writeNext(
            arrayOf(
                item.id,
                item.title,
                item.description,
                item.availability,
                item.condition,
                item.price,
                item.salesPrice,
                item.brand,
                item.googleProductCategory?.toString(),
                item.link,
                item.imageLink,
                item.additionalImageLink.joinToString(separator = "|")
            )
        )
    }
}
