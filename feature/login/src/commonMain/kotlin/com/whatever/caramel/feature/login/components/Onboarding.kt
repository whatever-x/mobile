package com.whatever.caramel.feature.login.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import caramel.feature.login.generated.resources.Res
import caramel.feature.login.generated.resources.step_1_description
import caramel.feature.login.generated.resources.step_1_title
import caramel.feature.login.generated.resources.step_2_description
import caramel.feature.login.generated.resources.step_2_title
import caramel.feature.login.generated.resources.step_3_description
import caramel.feature.login.generated.resources.step_3_title
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal enum class OnboardingStep(
    val res: DrawableResource,
    val title: StringResource,
    val description: StringResource
) {
    FIRST(
        res = Resources.Image.img_onboarding01,
        title = Res.string.step_1_title,
        description = Res.string.step_1_description
    ),
    SECOND(
        res = Resources.Image.img_onboarding02,
        title = Res.string.step_2_title,
        description = Res.string.step_2_description
    ),
    THIRD(
        res = Resources.Image.img_onboarding03,
        title = Res.string.step_3_title,
        description = Res.string.step_3_description
    ),
    ;
}

@Composable
internal fun OnboardingPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 36.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(resource = OnboardingStep.entries[page].res),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.xxl))

                Text(
                    text = stringResource(resource = OnboardingStep.entries[page].title),
                    style = CaramelTheme.typography.heading1,
                    color = CaramelTheme.color.text.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.s))

                Text(
                    text = stringResource(resource = OnboardingStep.entries[page].description),
                    style = CaramelTheme.typography.body3.regular,
                    color = CaramelTheme.color.text.primary
                )
            }
        }

        StepperIndicator(
            pageCount = OnboardingStep.entries.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun StepperIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = CaramelTheme.spacing.xs,
            alignment = Alignment.CenterHorizontally
        ),
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage

            val width by animateDpAsState(
                targetValue = if (isSelected) 20.dp else 6.dp,
                animationSpec = tween(durationMillis = 300)
            )
            val color by animateColorAsState(
                targetValue = if (isSelected) CaramelTheme.color.fill.primary
                else CaramelTheme.color.fill.tertiary,
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                modifier = Modifier
                    .height(height = 6.dp)
                    .width(width = width)
                    .clip(shape = CaramelTheme.shape.xs)
                    .background(color = color)
            )
        }
    }
}
