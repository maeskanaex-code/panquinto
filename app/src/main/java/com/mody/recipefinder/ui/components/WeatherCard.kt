package com.mody.recipefinder.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mody.recipefinder.domain.model.Meal
import com.mody.recipefinder.domain.model.Weather

/**
 * Weather + meal suggestion card.
 *
 * Shown at the top of the Home screen. Displays:
 *   [city, temperature, condition]  —————  [suggested meal thumbnail]
 *   [suggestion reason]
 *
 * Tapping the suggested meal opens its detail screen.
 */
@Composable
fun WeatherCard(
    weather: Weather,
    suggestedMeal: Meal?,
    suggestionReason: String?,
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left column — weather info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${weather.cityName} • ${weather.temperatureCelsius.toInt()}°C",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = weather.conditionText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                if (suggestionReason != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = suggestionReason,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Right side — suggested meal thumbnail (fixed 1:1 square)
            if (suggestedMeal != null) {
                Spacer(Modifier.width(12.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(72.dp)
                        .clickable { onMealClick(suggestedMeal.id) }
                ) {
                    AsyncImage(
                        model = suggestedMeal.thumbnail,
                        contentDescription = suggestedMeal.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Try today",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}
