package com.wutsi.ecommerce.catalog.endpoint

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import java.net.URL
import java.nio.charset.Charset
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/FacebookFeedController.sql"])
internal class FacebookFeedControllerTest : AbstractSecuredController() {
    @LocalServerPort
    val port: Int = 0

    @Test
    fun export() {
        val url = "http://localhost:$port/v1/feeds/1/facebook"
        val csv = URL(url).readText(Charset.defaultCharset())
        assertEquals(
            """
                "id","title","description","availability","condition","price","sales_price","link","image_link","brand"
                "101","Product 101","This is the summary 101","out of stock","new","1,100 XAF","","https://www.wutsi.me/product?id=101","https://img.com/1.png","Unknown Brand"
                "100","Product 100","This is the summary 100","in stock","new","1,500 XAF","1,000 XAF","https://www.wutsi.me/product?id=100","https://img.com/1.png","Unknown Brand"
            """.trimIndent(),
            csv.trimIndent()
        )
    }
}
