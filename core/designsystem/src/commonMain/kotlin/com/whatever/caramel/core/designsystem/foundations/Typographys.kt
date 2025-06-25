package com.whatever.caramel.core.designsystem.foundations

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.ibm_plex_sans_semi_bold
import caramel.core.designsystem.generated.resources.ibm_plex_sans_text
import org.jetbrains.compose.resources.Font

@Composable
internal fun IbmPlexSans(): FontFamily =
    FontFamily(
        Font(
            resource = Res.font.ibm_plex_sans_semi_bold,
            weight = FontWeight.W600,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.ibm_plex_sans_text,
            weight = FontWeight(450),
            style = FontStyle.Normal
        ),
    )