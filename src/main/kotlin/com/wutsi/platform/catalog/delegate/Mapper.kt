package com.wutsi.platform.catalog.delegate

import com.wutsi.platform.catalog.dto.Category
import com.wutsi.platform.catalog.dto.CategorySummary
import com.wutsi.platform.catalog.dto.PictureSummary
import com.wutsi.platform.catalog.dto.Product
import com.wutsi.platform.catalog.dto.ProductSummary
import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.entity.PictureEntity
import com.wutsi.platform.catalog.entity.ProductEntity

fun PictureEntity.toPictureSummary() = PictureSummary(
    id = this.id ?: -1,
    url = this.url
)

fun ProductEntity.toProductSummary() = ProductSummary(
    id = this.id ?: -1,
    accountId = this.accountId,
    title = this.title,
    summary = this.summary,
    price = this.price,
    comparablePrice = this.comparablePrice,
    currency = this.currency,
    created = this.created,
    updated = this.updated,
    thumbnail = this.thumbnail?.toPictureSummary()
)

fun ProductEntity.toProduct() = Product(
    id = this.id ?: -1,
    accountId = this.accountId,
    title = this.title,
    price = this.price,
    comparablePrice = this.comparablePrice,
    currency = this.currency,
    created = this.created,
    updated = this.updated,
    categoryId = this.categoryId,
    summary = this.summary,
    description = this.description,
    thumbnail = this.thumbnail?.toPictureSummary(),
    pictures = this.pictures.filter { !it.isDeleted }.map { it.toPictureSummary() },
    visible = this.visible,
    categories = this.categories.filter { !it.isDeleted }.map { it.toCategorySummary() },
)

fun CategoryEntity.toCategory() = Category(
    id = this.id ?: -1,
    title = this.title
)

fun CategoryEntity.toCategorySummary() = CategorySummary(
    id = this.id ?: -1,
    title = this.title
)
