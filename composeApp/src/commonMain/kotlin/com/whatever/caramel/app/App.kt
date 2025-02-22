// FIXME: 앱 이름 정해지고 app 패키지이름 변경 예정
package com.whatever.caramel.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whatever.caramel.feat.sample.presentation.SampleRoute

@Composable
fun App() {
    MaterialTheme {
        SampleRoute()
    }
}

