package com.whatever.caramel.feat.main.editor

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import app.composeapp.generated.resources.Res
import app.composeapp.generated.resources.test_tab_item4
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.stringResource

object EditorTab : Tab {
    @Composable
    override fun Content() {
        Navigator(EditorScreenRoot())
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.test_tab_item4)
            val icon = rememberVectorPainter(Icons.Default.Add)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }
}