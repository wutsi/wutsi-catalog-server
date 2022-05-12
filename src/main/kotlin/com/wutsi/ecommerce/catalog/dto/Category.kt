package com.wutsi.ecommerce.catalog.dto

data class Category(
    val id: Long = 0,
    val title: String = "",
    val parentId: Long? = null,
    val productCount: Int = 0,
    val publishedProductCount: Int = 0,
    val googleProductCategoryId: Long? = null
)
