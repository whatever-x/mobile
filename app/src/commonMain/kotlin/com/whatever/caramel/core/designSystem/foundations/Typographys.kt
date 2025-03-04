package com.whatever.caramel.core.designSystem.foundations

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import caramel.app.generated.resources.Res
import caramel.app.generated.resources.ibm_plex_sans_bold
import caramel.app.generated.resources.ibm_plex_sans_regular
import org.jetbrains.compose.resources.Font

@Composable
internal fun IbmPlexSans(): FontFamily =
    FontFamily(
        Font(
            resource = Res.font.ibm_plex_sans_bold,
            weight = FontWeight.W600,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.ibm_plex_sans_regular,
            weight = FontWeight(450),
            style = FontStyle.Normal
        ),
    )