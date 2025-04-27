package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun LazyListScope.Quiz(
    
) {
    item(key = "Quiz") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 376.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Quiz 예정"
            )
        }
    }
}