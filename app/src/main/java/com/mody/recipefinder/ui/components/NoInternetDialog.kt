package com.mody.recipefinder.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * Non-dismissible dialog telling the user there's no internet.
 * Only the "Retry" button closes it — and only if the caller decides
 * the user is now online. Tapping outside does nothing.
 */
@Composable
fun NoInternetDialog(
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* intentionally no-op: force the user to notice */ },
        title = {
            Text(
                text = "No Internet Connection",
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = "Panquinto needs an internet connection to fetch recipes and weather. " +
                    "Please check your Wi-Fi or mobile data and try again.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("Retry")
            }
        },
        dismissButton = null
    )
}
