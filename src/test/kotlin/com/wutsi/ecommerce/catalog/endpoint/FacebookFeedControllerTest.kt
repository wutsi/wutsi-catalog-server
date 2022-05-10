package com.wutsi.ecommerce.catalog.endpoint

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.platform.account.WutsiAccountApi
import com.wutsi.platform.account.dto.Account
import com.wutsi.platform.account.dto.GetAccountResponse
import com.wutsi.platform.account.entity.AccountStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
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

    @MockBean
    private lateinit var accountApi: WutsiAccountApi

    private lateinit var url: String

    @BeforeEach
    override fun setUp() {
        super.setUp()

        url = "http://localhost:$port/v1/feeds/1/facebook"
    }

    @Test
    fun export() {
        // GIVEN
        val account = createAccount()
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        val csv = URL(url).readText(Charset.defaultCharset())

        // GIVEN
        assertEquals(
            """
                "id","title","description","availability","condition","price","sale_price","brand","google_product_category","link","image_link","additional_image_link"
                "101","Product 101","This is the summary 101","out of stock","new","1,100 XAF",,"Nike",,"https://www.wutsi.me/product?id=101","https://img.com/100.png",""
                "100","Product 100","This is the summary 100","in stock","new","1,500 XAF","1,000 XAF","Nike",,"https://www.wutsi.me/product?id=100","https://img.com/100.png","https://img.com/101.png|https://img.com/103.png"
            """.trimIndent(),
            csv.trimIndent()
        )
    }

    @Test
    fun accountSuspended() {
        // GIVEN
        val account = createAccount(status = AccountStatus.SUSPENDED)
        doReturn(GetAccountResponse(account)).whenever(accountApi).getAccount(any())

        // WHEN
        val csv = URL(url).readText(Charset.defaultCharset())

        // GIVEN
        assertEquals(
            """
                "id","title","description","availability","condition","price","sale_price","brand","google_product_category","link","image_link","additional_image_link"
            """.trimIndent(),
            csv.trimIndent()
        )
    }

    private fun createAccount(
        id: Long = 1,
        displayName: String = "Nike",
        status: AccountStatus = AccountStatus.ACTIVE
    ) = Account(
        id = id,
        displayName = displayName,
        status = status.name
    )
}
