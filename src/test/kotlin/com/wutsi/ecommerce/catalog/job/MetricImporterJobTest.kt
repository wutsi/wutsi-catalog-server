package com.wutsi.ecommerce.catalog.job

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.ecommerce.catalog.entity.MetricType
import com.wutsi.ecommerce.catalog.service.MetricImporterOverall
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql"])
internal class MetricImporterJobTest {
    @MockBean
    private lateinit var overall: MetricImporterOverall

    @Autowired
    private lateinit var job: MetricImporterJob

    @BeforeEach
    fun setUp() {
        doReturn(100L).whenever(overall).import(any())
    }

    @Test
    fun run() {
        job.run()

        verify(overall, times(3)).import(any())
        verify(overall).import(MetricType.SHARE)
        verify(overall).import(MetricType.CHAT)
        verify(overall).import(MetricType.VIEW)
    }
}
