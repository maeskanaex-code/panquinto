package com.mody.recipefinder.data.mapper

import com.mody.recipefinder.data.local.FavoriteMealEntity
import com.mody.recipefinder.data.remote.dto.CategoryDto
import com.mody.recipefinder.data.remote.dto.MealDto
import com.mody.recipefinder.domain.model.Category
import com.mody.recipefinder.domain.model.Ingredient
import com.mody.recipefinder.domain.model.Meal

// ---------------------------------------------------------------------------
// DTO -> Domain
// ---------------------------------------------------------------------------

/**
 * Turns the raw 20-slot ingredient JSON into a clean List<Ingredient>.
 *
 * TheMealDB gives us strIngredient1..20 and strMeasure1..20 as separate
 * fields, most of them empty strings. We:
 *   1. Collect all 20 ingredient slots into a list.
 *   2. Collect all 20 measure slots into a list.
 *   3. zip them so index i of ingredients pairs with index i of measures.
 *   4. Drop pairs where the ingredient name is blank.
 */
fun MealDto.toDomain(): Meal {
    val rawIngredients = listOf(
        ingredient1, ingredient2, ingredient3, ingredient4, ingredient5,
        ingredient6, ingredient7, ingredient8, ingredient9, ingredient10,
        ingredient11, ingredient12, ingredient13, ingredient14, ingredient15,
        ingredient16, ingredient17, ingredient18, ingredient19, ingredient20
    )
    val rawMeasures = listOf(
        measure1, measure2, measure3, measure4, measure5,
        measure6, measure7, measure8, measure9, measure10,
        measure11, measure12, measure13, measure14, measure15,
        measure16, measure17, measure18, measure19, measure20
    )

    val ingredients = rawIngredients
        .zip(rawMeasures)
        .mapNotNull { (name, measure) ->
            val cleanName = name?.trim().orEmpty()
            if (cleanName.isEmpty()) return@mapNotNull null
            Ingredient(
                name = cleanName,
                measure = measure?.trim().orEmpty()
            )
        }

    return Meal(
        id = id,
        name = name,
        thumbnail = thumbnail,
        category = category?.takeIf { it.isNotBlank() },
        area = area?.takeIf { it.isNotBlank() },
        instructions = instructions?.takeIf { it.isNotBlank() },
        ingredients = ingredients
    )
}

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    thumbnail = thumbnail
)

// ---------------------------------------------------------------------------
// Domain <-> Entity (Room)
// ---------------------------------------------------------------------------

private const val INGREDIENT_SEPARATOR = "|||"
private const val FIELD_SEPARATOR = ";;;"

/**
 * Meal -> FavoriteMealEntity.
 *
 * Room can't store a List<Ingredient> directly. We flatten it to a
 * single string with two separators:
 *   - ";;;" separates ingredients
 *   - "|||" separates name from measure
 *
 * Example: "Chicken|||1.2 kg;;;Onion|||5 thinly sliced"
 *
 * On the way back (toDomain below), we split the same way.
 * Tradeoff: if an ingredient name or measure ever contains ";;;" or
 * "|||", parsing breaks. TheMealDB never does, but a proper fix is a
 * Room @TypeConverter using Gson. That's a good follow-up PR.
 */
fun Meal.toEntity(): FavoriteMealEntity = FavoriteMealEntity(
    id = id,
    name = name,
    thumbnail = thumbnail,
    category = category,
    area = area,
    instructions = instructions,
    ingredientsFlattened = ingredients.joinToString(FIELD_SEPARATOR) { ing ->
        "${ing.name}$INGREDIENT_SEPARATOR${ing.measure}"
    }
)

/**
 * FavoriteMealEntity -> Meal. Reverses the flattening above.
 * Anything malformed becomes an empty list rather than crashing.
 */
fun FavoriteMealEntity.toDomain(): Meal = Meal(
    id = id,
    name = name,
    thumbnail = thumbnail,
    category = category,
    area = area,
    instructions = instructions,
    ingredients = parseIngredients(ingredientsFlattened)
)

private fun parseIngredients(flat: String): List<Ingredient> {
    if (flat.isBlank()) return emptyList()
    return flat.split(FIELD_SEPARATOR).mapNotNull { pair ->
        val parts = pair.split(INGREDIENT_SEPARATOR)
        if (parts.size != 2) return@mapNotNull null
        val name = parts[0].trim()
        val measure = parts[1].trim()
        if (name.isEmpty()) return@mapNotNull null
        Ingredient(name = name, measure = measure)
    }
}
