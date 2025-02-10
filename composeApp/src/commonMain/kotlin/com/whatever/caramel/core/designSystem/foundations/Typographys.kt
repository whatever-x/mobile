package com.whatever.caramel.core.designSystem.foundations

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import app.composeapp.generated.resources.Res
import app.composeapp.generated.resources.line_seed_kr_bd
import app.composeapp.generated.resources.line_seed_kr_rg
import app.composeapp.generated.resources.line_seed_kr_th
import org.jetbrains.compose.resources.Font

// @ham2174 TODO : font 확정 시 .ttf 파일 추가 및 fontFamily 정의
@Composable
internal fun LineSeedKrFontFamily(): FontFamily =
    FontFamily(
        Font(
            resource = Res.font.line_seed_kr_bd,
            weight = FontWeight.W700,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.line_seed_kr_rg,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.line_seed_kr_th,
            weight = FontWeight.W100,
            style = FontStyle.Normal
        )
    )