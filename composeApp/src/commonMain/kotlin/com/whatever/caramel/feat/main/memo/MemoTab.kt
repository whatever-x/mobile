package com.whatever.caramel.feat.main.memo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import app.composeapp.generated.resources.Res
import app.composeapp.generated.resources.test_tab_item3
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.stringResource

object MemoTab : Tab{
    @Composable
    override fun Content() {
        Navigator(MemoScreenRoot())
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.test_tab_item3)
            val icon = rememberVectorPainter(Icons.Default.Menu)


            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }
}