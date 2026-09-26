package com.mody.recipefinder.domain

/**
 * Rule-based natural-language parser for the recipe assistant.
 *
 * No AI. No external calls. Just keyword matching against two dictionaries.
 * Turns things like "quick dinner for kids" into a structured QueryIntent.
 */
object QueryParser {

    private val CATEGORY_MAP: Map<String, String> = mapOf(
        "kid"         to "Dessert",
        "kids"        to "Dessert",
        "children"    to "Dessert",
        "child"       to "Dessert",
        "family"      to "Dessert",
        "sweet"       to "Dessert",
        "dessert"     to "Dessert",
        "treat"       to "Dessert",

        "healthy"     to "Seafood",
        "light"       to "Seafood",
        "fresh"       to "Seafood",
        "fish"        to "Seafood",
        "seafood"     to "Seafood",

        "comfort"     to "Beef",
        "warm"        to "Beef",
        "hearty"      to "Beef",
        "filling"     to "Beef",
        "rich"        to "Beef",
        "steak"       to "Beef",

        "spicy"       to "Chicken",
        "hot"         to "Chicken",

        "vegetarian"  to "Vegetarian",
        "veggie"      to "Vegetarian",
        "vegan"       to "Vegan",
        "plant"       to "Vegan",

        "starter"     to "Starter",
        "appetizer"   to "Starter",
        "breakfast"   to "Breakfast",
        "morning"     to "Breakfast",

        "side"        to "Side",
        "snack"       to "Side",

        "dinner"      to "Chicken",
        "lunch"       to "Chicken",
        "main"        to "Chicken"
    )

    private val INGREDIENT_MAP: Map<String, String> = mapOf(
        "chicken"   to "chicken",
        "beef"      to "beef",
        "pork"      to "pork",
        "lamb"      to "lamb",
        "fish"      to "fish",
        "salmon"    to "salmon",
        "shrimp"    to "shrimp",
        "prawn"     to "prawn",
        "rice"      to "rice",
        "pasta"     to "pasta",
        "spaghetti" to "spaghetti",
        "noodle"    to "noodles",
        "noodles"   to "noodles",
        "bread"     to "bread",
        "cheese"    to "cheese",
        "egg"       to "egg",
        "eggs"      to "egg",
        "potato"    to "potato",
        "potatoes"  to "potato",
        "tomato"    to "tomato",
        "onion"     to "onion",
        "garlic"    to "garlic",
        "mushroom"  to "mushrooms",
        "carrot"    to "carrot"
    )

    private val STOPWORDS: Set<String> = setOf(
        "a", "an", "the", "for", "of", "with", "and", "or", "to",
        "i", "me", "my", "want", "need", "give", "show", "find",
        "something", "some", "any", "please", "can", "you",
        "recipe", "recipes", "meal", "meals", "food", "dish", "dishes",
        "good", "great", "nice", "best", "really",
        "is", "are", "was", "be", "have", "has", "do", "does",
        "what", "which", "that", "this", "it", "its",
        "quick", "fast", "easy", "simple"
    )

    fun parse(input: String): QueryIntent {
        val original = input.trim()
        val words = original.lowercase()
            .split(Regex("\\W+"))
            .filter { it.isNotBlank() }

        var category: String? = null
        var ingredient: String? = null
        val leftovers = mutableListOf<String>()

        for (word in words) {
            when {
                INGREDIENT_MAP.containsKey(word) && ingredient == null -> {
                    ingredient = INGREDIENT_MAP[word]
                }
                CATEGORY_MAP.containsKey(word) && category == null -> {
                    category = CATEGORY_MAP[word]
                }
                word !in STOPWORDS && word.length > 2 -> {
                    leftovers += word
                }
            }
        }

        if (ingredient != null) {
            category = null
        }

        val reason = buildReason(original, category, ingredient, leftovers)

        return QueryIntent(
            category = category,
            ingredient = ingredient,
            keywords = leftovers,
            reason = reason,
            originalQuery = original
        )
    }

    private fun buildReason(
        original: String,
        category: String?,
        ingredient: String?,
        keywords: List<String>
    ): String = when {
        ingredient != null && keywords.isNotEmpty() ->
            "Found ${ingredient} dishes — ${keywords.joinToString(" ")}"

        ingredient != null ->
            "Found dishes with ${ingredient}"

        category != null && keywords.isNotEmpty() ->
            "Found ${category.lowercase()} dishes — ${keywords.joinToString(" ")}"

        category != null ->
            "Found ${category.lowercase()} dishes"

        keywords.isNotEmpty() ->
            "Searching for ${keywords.joinToString(" ")} recipes"

        else ->
            "Here's what we found"
    }
}
