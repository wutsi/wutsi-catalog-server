package com.wutsi.platform.catalog.delegate

import com.wutsi.platform.catalog.dto.Category
import com.wutsi.platform.catalog.dto.CategorySummary
import com.wutsi.platform.catalog.dto.PictureSummary
import com.wutsi.platform.catalog.dto.Product
import com.wutsi.platform.catalog.dto.ProductSummary
import com.wutsi.platform.catalog.entity.CategoryEntity
import com.wutsi.platform.catalog.entity.PictureEntity
import com.wutsi.platform.catalog.entity.ProductEntity
import org.springframework.context.i18n.LocaleContextHolder

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
    thumbnail = this.thumbnail?.toPictureSummary(),
    categoryId = this.category.id ?: -1,
    subCategoryId = this.subCategory.id ?: -1,
    type = this.type.name,
    quantity = this.quantity,
    maxOrder = this.maxOrder
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
    summary = this.summary,
    description = this.description,
    thumbnail = this.thumbnail?.toPictureSummary(),
    pictures = this.pictures.filter { !it.isDeleted }.map { it.toPictureSummary() },
    visible = this.visible,
    category = this.category.toCategorySummary(),
    subCategory = this.subCategory.toCategorySummary(),
    type = this.type.name,
    quantity = this.quantity,
    maxOrder = this.maxOrder
)

fun CategoryEntity.toCategory() = Category(
    id = this.id ?: -1,
    parentId = this.parentId,
    title = this.toTitle()
)

fun CategoryEntity.toCategorySummary() = CategorySummary(
    id = this.id ?: -1,
    parentId = this.parentId,
    title = this.toTitle()
)

fun CategoryEntity.toTitle(): String {
    val language = LocaleContextHolder.getLocale().language
    return if (language == "fr")
        this.titleFrench
    else
        this.title
}
