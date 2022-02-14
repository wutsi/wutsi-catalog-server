package com.wutsi.platform.catalog.delegate

import com.wutsi.platform.catalog.dto.Category
import com.wutsi.platform.catalog.dto.CategorySummary
import com.wutsi.platform.catalog.dto.PictureSummary
import com.wutsi.platform.catalog.dto.Product
import com.wutsi.platform.catalog.dto.ProductSummary
import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.entity.PictureEntity
import com.wutsi.platform.catalog.entity.ProductEntity
import javax.servlet.http.HttpServletRequest

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

fun ProductEntity.toProduct(request: HttpServletRequest) = Product(
    id = this.id ?: -1,
    accountId = this.accountId,
    title = this.title,
    price = this.price,
    comparablePrice = this.comparablePrice,
    currency = this.currency,
    created = this.created,
    updated = this.updated,
    summary = this.summary,
    description = this.description,
    thumbnail = this.thumbnail?.toPictureSummary(),
    pictures = this.pictures.filter { !it.isDeleted }.map { it.toPictureSummary() },
    visible = this.visible,
    category = this.category.toCategorySummary(request),
    subCategory = this.subCategory.toCategorySummary(request),
)

fun CategoryEntity.toCategory(request: HttpServletRequest) = Category(
    id = this.id ?: -1,
    parentId = this.parentId,
    title = this.toTitle(request)
)

fun CategoryEntity.toCategorySummary(request: HttpServletRequest) = CategorySummary(
    id = this.id ?: -1,
    parentId = this.parentId,
    title = this.toTitle(request)
)

fun CategoryEntity.toTitle(request: HttpServletRequest): String =
    if (request.locale.language == "fr")
        this.titleFrench
    else
        this.title
