package com.mody.recipefinder.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * One category from categories.php.
 *
 * TheMealDB prefixes its string fields with "str". @SerializedName maps
 * each JSON key to a clean Kotlin property name.
 */
data class CategoryDto(
    @SerializedName("idCategory")
    val id: String,

    @SerializedName("strCategory")
    val name: String,

    @SerializedName("strCategoryThumb")
    val thumbnail: String,

    @SerializedName("strCategoryDescription")
    val description: String
)
