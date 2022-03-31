package com.wutsi.ecommerce.catalog.delegate

import com.wutsi.ecommerce.catalog.dto.Category
import com.wutsi.ecommerce.catalog.dto.CategorySummary
import com.wutsi.ecommerce.catalog.dto.PictureSummary
import com.wutsi.ecommerce.catalog.dto.Product
import com.wutsi.ecommerce.catalog.dto.ProductSummary
import com.wutsi.ecommerce.catalog.dto.Section
import com.wutsi.ecommerce.catalog.dto.SectionSummary
import com.wutsi.ecommerce.catalog.entity.CategoryEntity
import com.wutsi.ecommerce.catalog.entity.PictureEntity
import com.wutsi.ecommerce.catalog.entity.ProductEntity
import com.wutsi.ecommerce.catalog.entity.SectionEntity
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
    comparablePrice = if (this.comparablePrice != null && this.price != null && this.comparablePrice!! > this.price!!)
        this.comparablePrice
    else
        null,
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
    maxOrder = this.maxOrder,
    sections = this.sections.filter { !it.isDeleted }.map { it.toSectionSummary() }
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

fun SectionEntity.toSectionSummary() = SectionSummary(
    id = this.id ?: -1,
    title = this.title,
    productCount = this.productCount,
    sortOrder = this.sortOrder
)

fun SectionEntity.toSection() = Section(
    id = this.id ?: -1,
    title = this.title,
    productCount = this.productCount,
    sortOrder = this.sortOrder
)
