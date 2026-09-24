package com.mody.recipefinder.domain.model

/**
 * Clean category model. Mapped from CategoryDto.
 */
data class Category(
    val id: String,
    val name: String,
    val thumbnail: String
)
