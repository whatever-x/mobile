@file:OptIn(ExperimentalMaterial3Api::class)

package com.whatever.caramel.feature.content.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.whatever.caramel.core.designsystem.components.CaramelButton
import com.whatever.caramel.core.designsystem.components.CaramelButtonSize
import com.whatever.caramel.core.designsystem.components.CaramelButtonType
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.CaramelDatePicker
import com.whatever.caramel.core.ui.picker.CaramelTimePicker
import com.whatever.caramel.core.ui.picker.TimeUiState
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.feature.content.create.components.ContentTextArea
import com.whatever.caramel.feature.content.create.components.CreateMode
import com.whatever.caramel.feature.content.create.components.CreateModeSwitch
import com.whatever.caramel.feature.content.create.components.DateBottomSheet
import com.whatever.caramel.feature.content.create.components.SelectableTagChipRow
import com.whatever.caramel.feature.content.create.components.TitleTextField
import com.whatever.caramel.feature.content.create.mvi.ContentIntent
import com.whatever.caramel.feature.content.create.mvi.ContentState
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ContentScreen(
    state: ContentState,
    onIntent: (ContentIntent) -> Unit
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = false
    )
    val contentFocusRequester = FocusRequester()
    val isKeyboardVisible by rememberKeyboardVisibilityState()
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { keyboardController?.hide() }
            },
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            Column {
                CaramelTopBar(
                    modifier = Modifier.statusBarsPadding(),
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable(
                                onClick = { onIntent(ContentIntent.ClickCloseButton) },
                                indication = null,
                                interactionSource = null
                            ),
                            painter = painterResource(resource = Resources.Icon.ic_cancel_24),
                            tint = CaramelTheme.color.icon.primary,
                            contentDescription = null
                        )
                    }
                )
                TitleTextField(
                    modifier = Modifier.padding(horizontal = CaramelTheme.spacing.xl),
                    value = state.title,
                    onValueChange = {
                        onIntent(ContentIntent.InputTitle(it))
                    },
                    onKeyboardAction = {
                        contentFocusRequester.requestFocus()
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = CaramelTheme.spacing.xl),
                    color = CaramelTheme.color.divider.primary
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CaramelTheme.spacing.xl)
                    .navigationBarsPadding()
                    .imePadding(),
            ) {
                AnimatedVisibility(
                    visible = isKeyboardVisible.not(),
                    enter = fadeIn(),
                    exit = ExitTransition.None,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        SelectableTagChipRow(
                            modifier = Modifier.fillMaxWidth(),
                            tags = state.tags,
                            selectedTags = state.selectedTags.toImmutableList(),
                            onTagClick = {
                                onIntent(ContentIntent.ClickTag(it))
                            }
                        )
                        Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.xl))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CreateModeSwitch(
                                createMode = state.createMode,
                                onCreateModeSelect = {
                                    onIntent(ContentIntent.SelectCreateMode(it))
                                }
                            )
                            when (state.createMode) {
                                CreateMode.MEMO -> {
                                    Text(
                                        text = "정해진 일정이 없어요",
                                        style = CaramelTheme.typography.body2.regular,
                                        color = CaramelTheme.color.text.disabledPrimary
                                    )
                                }

                                CreateMode.CALENDAR -> {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.s)
                                    ) {
                                        Text(
                                            modifier = Modifier.clickable { onIntent(ContentIntent.ClickDate) },
                                            text = state.date,
                                            style = CaramelTheme.typography.body2.regular,
                                            color = CaramelTheme.color.text.primary
                                        )
                                        Text(
                                            modifier = Modifier.clickable { onIntent(ContentIntent.ClickTime) },
                                            text = state.time,
                                            style = CaramelTheme.typography.body2.regular,
                                            color = CaramelTheme.color.text.primary
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.xl))
                    }
                }
                CaramelButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonType = if (state.isSaveButtonEnable) {
                        CaramelButtonType.Enabled1
                    } else {
                        CaramelButtonType.Disabled
                    },
                    buttonSize = CaramelButtonSize.Large,
                    text = "저장",
                    onClick = {
                        onIntent(ContentIntent.ClickSaveButton)
                    }
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = CaramelTheme.spacing.xl)
        ) {
            ContentTextArea(
                modifier = Modifier.weight(1f),
                value = state.content,
                onValueChange = {
                    onIntent(ContentIntent.InputContent(it))
                },
                focusRequester = contentFocusRequester,
                placeholder = "함께 하고 싶거나 기억하면 좋은 것들을 자유롭게 입력해 주세요.",
            )
        }
    }
    if (state.showDateDialog || state.showTimeDialog) {
        DateBottomSheet(
            sheetState = sheetState,
            onDismiss = { onIntent(ContentIntent.HideDateTimeDialog) },
            content = {
                when {
                    state.showDateDialog -> {
                        CaramelDatePicker(
                            modifier = Modifier
                                .padding(top = CaramelTheme.spacing.xxl)
                                .align(Alignment.CenterHorizontally),
                            dateUiState = DateUiState.from(state.dateTime),
                            onYearChanged = { year ->
                                onIntent(ContentIntent.OnYearChanged(year))
                            },
                            onDayChanged = { day ->
                                onIntent(ContentIntent.OnDayChanged(day))
                            },
                            onMonthChanged = { month ->
                                onIntent(ContentIntent.OnMonthChanged(month))
                            }
                        )
                    }

                    state.showTimeDialog -> {
                        CaramelTimePicker(
                            modifier = Modifier
                                .padding(top = CaramelTheme.spacing.xxl)
                                .align(Alignment.CenterHorizontally),
                            timeUiState = TimeUiState.from(state.dateTime),
                            onPeriodChanged = { period ->
                                onIntent(ContentIntent.OnPeriodChanged(period))
                            },
                            onHourChanged = { hour ->
                                onIntent(ContentIntent.OnHourChanged(hour))
                            },
                            onMinuteChanged = { minute ->
                                onIntent(ContentIntent.OnMinuteChanged(minute))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.l))
                CaramelButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = CaramelTheme.spacing.xl),
                    buttonType = CaramelButtonType.Enabled1,
                    buttonSize = CaramelButtonSize.Large,
                    text = "완료",
                    onClick = {
                        onIntent(ContentIntent.HideDateTimeDialog)
                    }
                )
            }
        )
    }
}

@Composable
fun rememberKeyboardVisibilityState(): State<Boolean> {
    val ime = WindowInsets.ime
    val density = LocalDensity.current
    val keyboardVisible = remember { mutableStateOf(false) }

    LaunchedEffect(ime) {
        snapshotFlow { ime.getBottom(density) }
            .collect { bottom ->
                keyboardVisible.value = bottom > 300
            }
    }

    return keyboardVisible
}