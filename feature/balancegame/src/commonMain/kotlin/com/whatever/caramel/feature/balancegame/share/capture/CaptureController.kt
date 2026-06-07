package com.whatever.caramel.feature.balancegame.share.capture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer

class CaptureController(
    val graphicsLayer: GraphicsLayer,
) {
    suspend fun capture(): ImageBitmap = graphicsLayer.toImageBitmap()
}

@Composable
fun rememberCaptureController(): CaptureController {
    val graphicsLayer = rememberGraphicsLayer()
    return remember(graphicsLayer) { CaptureController(graphicsLayer) }
}

fun Modifier.capturable(controller: CaptureController): Modifier =
    drawWithContent {
        val layer = controller.graphicsLayer
        layer.record {
            this@drawWithContent.drawContent()
        }
        drawLayer(layer)
    }
