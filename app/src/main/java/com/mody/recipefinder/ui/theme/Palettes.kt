package com.mody.recipefinder.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────
// WARM — soft peach header, orange accents, cream background
// (matches the current look)
// ─────────────────────────────────────────────────────────────

private val WarmLight = lightColorScheme(
    primary            = Color(0xFFE55B32),
    onPrimary          = Color(0xFFFFFFFF),
    primaryContainer   = Color(0xFFF7C19C),
    onPrimaryContainer = Color(0xFF33221A),
    secondary          = Color(0xFFD8532A),
    onSecondary        = Color(0xFFFFFFFF),
    background         = Color(0xFFFAF0E6),
    onBackground       = Color(0xFF2D1F17),
    surface            = Color(0xFFFFF8F2),
    onSurface          = Color(0xFF2D1F17),
    surfaceVariant     = Color(0xFFFFECE0),
    onSurfaceVariant   = Color(0xFFA08375),
    outline            = Color(0xFFF0D8CB),
    outlineVariant     = Color(0xFFFAF0E6)
)

private val WarmDark = darkColorScheme(
    primary            = Color(0xFFFFB088),
    onPrimary          = Color(0xFF3D1A00),
    primaryContainer   = Color(0xFF7A3A18),
    onPrimaryContainer = Color(0xFFFFE0CC),
    secondary          = Color(0xFFE55B32),
    onSecondary        = Color(0xFF2A1500),
    background         = Color(0xFF1A1410),
    onBackground       = Color(0xFFF5EBE0),
    surface            = Color(0xFF251D17),
    onSurface          = Color(0xFFF5EBE0),
    surfaceVariant     = Color(0xFF3A2E25),
    onSurfaceVariant   = Color(0xFFC9B8A8),
    outline            = Color(0xFF4A3D33),
    outlineVariant     = Color(0xFF3A2E25)
)

// ─────────────────────────────────────────────────────────────
// COOL — blue accents, cool light-gray / deep navy
// ─────────────────────────────────────────────────────────────

private val CoolLight = lightColorScheme(
    primary            = Color(0xFF3B6EA5),
    onPrimary          = Color(0xFFFFFFFF),
    primaryContainer   = Color(0xFFCFE2FF),
    onPrimaryContainer = Color(0xFF0A1B2E),
    secondary          = Color(0xFF4A7BB5),
    onSecondary        = Color(0xFFFFFFFF),
    background         = Color(0xFFF4F7FB),
    onBackground       = Color(0xFF1A2433),
    surface            = Color(0xFFFFFFFF),
    onSurface          = Color(0xFF1A2433),
    surfaceVariant     = Color(0xFFE2EAF3),
    onSurfaceVariant   = Color(0xFF6E7A8C),
    outline            = Color(0xFFD5DEE8),
    outlineVariant     = Color(0xFFE2EAF3)
)

private val CoolDark = darkColorScheme(
    primary            = Color(0xFF9CC7F0),
    onPrimary          = Color(0xFF0A1B2E),
    primaryContainer   = Color(0xFF2A4A6E),
    onPrimaryContainer = Color(0xFFCFE2FF),
    secondary          = Color(0xFF6E9AC8),
    onSecondary        = Color(0xFF0A1422),
    background         = Color(0xFF10161E),
    onBackground       = Color(0xFFE2EAF3),
    surface            = Color(0xFF182330),
    onSurface          = Color(0xFFE2EAF3),
    surfaceVariant     = Color(0xFF243140),
    onSurfaceVariant   = Color(0xFF9BA9BB),
    outline            = Color(0xFF33404F),
    outlineVariant     = Color(0xFF243140)
)

// ─────────────────────────────────────────────────────────────
// FOREST — green accents, earthy olive / deep moss
// ─────────────────────────────────────────────────────────────

private val ForestLight = lightColorScheme(
    primary            = Color(0xFF4A7C4E),
    onPrimary          = Color(0xFFFFFFFF),
    primaryContainer   = Color(0xFFC9E6C8),
    onPrimaryContainer = Color(0xFF0A1F0C),
    secondary          = Color(0xFF6B8F5F),
    onSecondary        = Color(0xFFFFFFFF),
    background         = Color(0xFFF4F7EE),
    onBackground       = Color(0xFF1A2416),
    surface            = Color(0xFFFFFFFF),
    onSurface          = Color(0xFF1A2416),
    surfaceVariant     = Color(0xFFE4EBD9),
    onSurfaceVariant   = Color(0xFF6E7A63),
    outline            = Color(0xFFD6DFC9),
    outlineVariant     = Color(0xFFE4EBD9)
)

private val ForestDark = darkColorScheme(
    primary            = Color(0xFF9FD9A2),
    onPrimary          = Color(0xFF0A1F0C),
    primaryContainer   = Color(0xFF2F5A32),
    onPrimaryContainer = Color(0xFFC9E6C8),
    secondary          = Color(0xFF8FBF84),
    onSecondary        = Color(0xFF12210F),
    background         = Color(0xFF101810),
    onBackground       = Color(0xFFE4EBD9),
    surface            = Color(0xFF1C261B),
    onSurface          = Color(0xFFE4EBD9),
    surfaceVariant     = Color(0xFF2C3829),
    onSurfaceVariant   = Color(0xFFA6B29C),
    outline            = Color(0xFF3A4636),
    outlineVariant     = Color(0xFF2C3829)
)

// ─────────────────────────────────────────────────────────────
// Resolver
// ─────────────────────────────────────────────────────────────

fun colorSchemeFor(theme: AppThemeName, dark: Boolean): ColorScheme {
    return when (theme) {
        AppThemeName.WARM -> if (dark) WarmDark else WarmLight
        AppThemeName.COOL -> if (dark) CoolDark else CoolLight
        AppThemeName.FOREST -> if (dark) ForestDark else ForestLight
    }
}

// Lightweight enum used by the palette resolver so Palettes.kt
// doesn't depend on the data.preferences package.
enum class AppThemeName { WARM, COOL, FOREST }
