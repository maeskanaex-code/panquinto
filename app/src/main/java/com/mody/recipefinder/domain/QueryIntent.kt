package com.mody.recipefinder.domain

/**
 * The parser's output: a structured description of what the user wants.
 *
 * The parser takes free-text input and distills it into these fields.
 * The ViewModel then uses them to query TheMealDB.
 */
data class QueryIntent(
    val category: String? = null,
    val ingredient: String? = null,
    val keywords: List<String> = emptyList(),
    val reason: String = "",
    val originalQuery: String = ""
) {
    /** True if the parser extracted anything meaningful from the input. */
    val isUnderstood: Boolean
        get() = category != null || ingredient != null || keywords.isNotEmpty()
}
