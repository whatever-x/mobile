@file:OptIn(ExperimentalMaterial3Api::class)

package com.whatever.caramel.feature.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
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
import com.whatever.caramel.feature.content.components.ContentTextArea
import com.whatever.caramel.feature.content.components.CreateModeSwitch
import com.whatever.caramel.feature.content.components.DateBottomSheet
import com.whatever.caramel.feature.content.components.SelectableTagChipRow
import com.whatever.caramel.feature.content.components.TitleTextField
import com.whatever.caramel.feature.content.mvi.ContentIntent
import com.whatever.caramel.feature.content.mvi.ContentState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CaramelTheme.color.background.primary)
            .systemBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CaramelTopBar(
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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = CaramelTheme.spacing.xl)
                    .padding(bottom = 50.dp)
                    .padding(bottom = CaramelTheme.spacing.xl * 2)
            ) {
                TitleTextField(
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
                ContentTextArea(
                    modifier = Modifier
                        .fillMaxHeight()
                        .imePadding()
                        .focusRequester(contentFocusRequester),
                    value = state.content,
                    onValueChange = {
                        onIntent(ContentIntent.InputContent(it))
                    },
                    placeholder = "함께 하고 싶거나 기억하면 좋은 것들을 자유롭게 입력해 주세요.",
                )
                Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.xl))
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
                        ContentState.CreateMode.MEMO -> {
                            Text(
                                text = "정해진 일정이 없어요",
                                style = CaramelTheme.typography.body2.regular,
                                color = CaramelTheme.color.text.disabledPrimary
                            )
                        }

                        ContentState.CreateMode.CALENDAR -> {
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
                                dateUiState = state.dateTime.toDateUiState(),
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
                                timeUiState = state.dateTime.toTimeUiState(),
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
        CaramelButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = CaramelTheme.spacing.xl)
                .align(Alignment.BottomCenter)
                .imePadding(),
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

private fun LocalDateTime.toDateUiState(): DateUiState {
    return DateUiState(
        year = this.year,
        month = this.monthNumber,
        day = this.dayOfMonth
    )
}

private fun LocalDateTime.toTimeUiState(): TimeUiState {
    val currentHour = this.hour
    val period = if (currentHour < 12) "오전" else "오후"
    val hourIn12 = if (currentHour == 0 || currentHour == 12) 12 else currentHour % 12
    return TimeUiState(
        period = period,
        hour = hourIn12.toString(),
        minute = this.minute.toString()
    )
}