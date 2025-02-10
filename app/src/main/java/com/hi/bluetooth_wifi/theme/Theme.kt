package com.hi.bluetooth_wifi.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    primaryContainer = SurfaceDark,
    onPrimaryContainer = OnSurfaceLight,
    secondary = PurpleGrey80,
    secondaryContainer = SurfaceDark,
    onSecondaryContainer = OnSurfaceLight,
    tertiary = Pink80,
    tertiaryContainer = SurfaceDark,
    onTertiaryContainer = OnSurfaceLight,
    background = BackgroundDark,
    surface = SurfaceDark,
    onSurface = TestDark,
    onPrimary = TestDark,
    onSecondary = TestDark,
    onTertiary = TestDark,
    onBackground = TestDark,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    primaryContainer = SurfaceLight,
    onPrimaryContainer = BackgroundLight,
    secondary = PurpleGrey40,
    secondaryContainer = SurfaceLight,
    onSecondaryContainer = BackgroundLight,
    tertiary = Pink40,
    tertiaryContainer = SurfaceLight,
    onTertiaryContainer = BackgroundLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onSurface = TestLight,
    onPrimary = TestLight,
    onSecondary = TestLight,
    onTertiary = TestLight,
    onBackground = TestLight,



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
// 定義字體大小的比例
private const val FONT_SCALE = 1.5f


private val CustomTypography = Typography(
    displayLarge = TextStyle(fontSize = (57 * FONT_SCALE).sp),
    displayMedium = TextStyle(fontSize = (45 * FONT_SCALE).sp),
    displaySmall = TextStyle(fontSize = (36 * FONT_SCALE).sp),
    headlineLarge = TextStyle(fontSize = (32 * FONT_SCALE).sp),
    headlineMedium = TextStyle(fontSize = (28 * FONT_SCALE).sp),
    headlineSmall = TextStyle(fontSize = (24 * FONT_SCALE).sp),
    titleLarge = TextStyle(fontSize = (22 * FONT_SCALE).sp),
    titleMedium = TextStyle(fontSize = (16 * FONT_SCALE).sp),
    titleSmall = TextStyle(fontSize = (14 * FONT_SCALE).sp),
    bodyLarge = TextStyle(fontSize = (16 * FONT_SCALE).sp),
    bodyMedium = TextStyle(fontSize = (14 * FONT_SCALE).sp),
    bodySmall = TextStyle(fontSize = (12 * FONT_SCALE).sp),
    labelLarge = TextStyle(fontSize = (14 * FONT_SCALE).sp),
    labelMedium = TextStyle(fontSize = (12 * FONT_SCALE).sp),
    labelSmall = TextStyle(fontSize = (11 * FONT_SCALE).sp),
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val textColor = colorScheme.onSurface // 使用 onSurface 來管理文字顏色

    CompositionLocalProvider(LocalContentColor provides textColor,
            LocalTextStyle provides MaterialTheme.typography.titleLarge // 預設使用 bodyMedium
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = CustomTypography,
            content = content
        )
    }
}
