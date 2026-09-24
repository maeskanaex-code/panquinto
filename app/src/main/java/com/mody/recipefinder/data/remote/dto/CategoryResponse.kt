package com.mody.recipefinder.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Wrapper for categories.php.
 * Example: { "categories": [ {...}, {...} ] }
 */
data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<CategoryDto>?
)
