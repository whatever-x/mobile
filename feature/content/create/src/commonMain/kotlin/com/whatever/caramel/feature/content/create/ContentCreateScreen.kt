@file:OptIn(ExperimentalMaterial3Api::class)

package com.whatever.caramel.feature.content.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.ic_check_14
import caramel.core.designsystem.generated.resources.ic_check_18
import caramel.core.designsystem.generated.resources.ic_profile_18
import caramel.core.designsystem.generated.resources.ic_tag_18
import com.whatever.caramel.core.designsystem.components.CaramelButton
import com.whatever.caramel.core.designsystem.components.CaramelButtonSize
import com.whatever.caramel.core.designsystem.components.CaramelButtonType
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.ui.content.ContentAssigneeChipRow
import com.whatever.caramel.core.ui.content.ContentTextArea
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.content.CreateModeSwitch
import com.whatever.caramel.core.ui.content.DateBottomSheet
import com.whatever.caramel.core.ui.content.SelectableTagChipRow
import com.whatever.caramel.core.ui.content.TagChip
import com.whatever.caramel.core.ui.content.TitleTextField
import com.whatever.caramel.core.ui.picker.CaramelDatePicker
import com.whatever.caramel.core.ui.picker.CaramelTimePicker
import com.whatever.caramel.core.ui.util.rememberKeyboardVisibleState
import com.whatever.caramel.feature.content.create.component.ContentScheduleInfo
import com.whatever.caramel.feature.content.create.mvi.ContentCreateIntent
import com.whatever.caramel.feature.content.create.mvi.ContentCreateState
import com.whatever.caramel.feature.content.create.mvi.ScheduleDateTimeType
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ContentScreen(
    state: ContentCreateState,
    onIntent: (ContentCreateIntent) -> Unit,
) {
    val sheetState =
        rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false,
        )
    val contentFocusRequester = FocusRequester()
    val isKeyboardVisible by rememberKeyboardVisibleState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val contentSettingScrollState = rememberScrollState()
    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
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
                            modifier =
                                Modifier.clickable(
                                    onClick = { onIntent(ContentCreateIntent.ClickCloseButton) },
                                    indication = null,
                                    interactionSource = null,
                                ),
                            painter = painterResource(resource = Resources.Icon.ic_cancel_24),
                            tint = CaramelTheme.color.icon.primary,
                            contentDescription = null,
                        )
                    },
                )
                TitleTextField(
                    modifier = Modifier.padding(horizontal = CaramelTheme.spacing.xl),
                    value = state.title,
                    onValueChange = {
                        onIntent(ContentCreateIntent.InputTitle(it))
                    },
                    onKeyboardAction = {
                        contentFocusRequester.requestFocus()
                    },
                )
                HorizontalDivider(
                    modifier =
                        Modifier.padding(
                            vertical = CaramelTheme.spacing.xl,
                            horizontal = CaramelTheme.spacing.xl,
                        ),
                    color = CaramelTheme.color.divider.primary,
                )
            }
        },
        bottomBar = {
            CaramelButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(all = CaramelTheme.spacing.xl),
                buttonType =
                    if (state.isSaveButtonEnable) {
                        CaramelButtonType.Enabled1
                    } else {
                        CaramelButtonType.Disabled
                    },
                buttonSize = CaramelButtonSize.Large,
                text = "저장",
                onClick = {
                    onIntent(ContentCreateIntent.ClickSaveButton)
                },
            )
        },
    ) { contentPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = contentPadding.calculateTopPadding())
                    .padding(horizontal = CaramelTheme.spacing.xl),
        ) {
            ContentTextArea(
                modifier = Modifier.weight(1f),
                value = state.content,
                onValueChange = {
                    onIntent(ContentCreateIntent.InputContent(it))
                },
                focusRequester = contentFocusRequester,
                placeholder = "함께 하고 싶거나 기억하면 좋은 것들을\n자유롭게 입력해 주세요.",
            )

            Column(
                modifier =
                    Modifier
                        .heightIn(max = 215.dp)
                        .fillMaxWidth()
                        .padding(bottom = contentPadding.calculateBottomPadding())
                        .verticalScroll(contentSettingScrollState),
            ) {
                AnimatedVisibility(
                    visible = isKeyboardVisible.not(),
                    enter = fadeIn(),
                    exit = ExitTransition.None,
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(
                                    space = CaramelTheme.spacing.l,
                                    alignment = Alignment.CenterHorizontally,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_profile_18),
                                contentDescription = null,
                            )
                            ContentAssigneeChipRow(
                                modifier = Modifier.fillMaxWidth(),
                                selectedAssigneeChip = state.selectedAssignee,
                                onAssigneeChipClick = { assignee ->
                                    onIntent(ContentCreateIntent.ClickAssignee(assignee = assignee))
                                },
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = CaramelTheme.spacing.m),
                            color = CaramelTheme.color.divider.primary,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement =
                                    Arrangement.spacedBy(
                                        space = CaramelTheme.spacing.l,
                                        alignment = Alignment.Start,
                                    ),
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_check_18),
                                    contentDescription = null,
                                )
                                CreateModeSwitch(
                                    createMode = state.createMode,
                                    onCreateModeSelect = {
                                        onIntent(ContentCreateIntent.SelectCreateMode(it))
                                    },
                                )
                            }
                            when (state.createMode) {
                                CreateMode.MEMO -> {
                                    Text(
                                        text = "메모로 저장할게요",
                                        style = CaramelTheme.typography.body2.regular,
                                        color = CaramelTheme.color.text.disabledPrimary,
                                    )
                                }

                                CreateMode.CALENDAR -> {
                                    val (checkColor, textColor, borderColor) =
                                        if (state.isAllDay) {
                                            Triple(
                                                CaramelTheme.color.icon.primary,
                                                CaramelTheme.color.text.primary,
                                                CaramelTheme.color.fill.primary,
                                            )
                                        } else {
                                            Triple(
                                                CaramelTheme.color.fill.disabledPrimary,
                                                CaramelTheme.color.text.disabledPrimary,
                                                CaramelTheme.color.fill.disabledPrimary,
                                            )
                                        }
                                    Row(
                                        modifier =
                                            Modifier
                                                .clickable(
                                                    indication = null,
                                                    interactionSource = null,
                                                    onClick = { onIntent(ContentCreateIntent.ClickAllDayButton) },
                                                ).border(
                                                    width = 1.dp,
                                                    color = borderColor,
                                                    shape = CaramelTheme.shape.s,
                                                ).padding(
                                                    horizontal = CaramelTheme.spacing.m,
                                                    vertical = CaramelTheme.spacing.xs,
                                                ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        Icon(
                                            painter = painterResource(resource = Res.drawable.ic_check_14),
                                            tint = checkColor,
                                            contentDescription = null,
                                        )
                                        Spacer(modifier = Modifier.size(size = CaramelTheme.spacing.xxs))
                                        Text(
                                            text = "하루종일",
                                            style = CaramelTheme.typography.body4.regular,
                                            color = textColor,
                                        )
                                    }
                                }
                            }
                        }

                        if (state.createMode == CreateMode.CALENDAR) {
                            Column(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = CaramelTheme.spacing.m),
                                verticalArrangement =
                                    Arrangement.spacedBy(
                                        space = CaramelTheme.spacing.s,
                                    ),
                            ) {
                                ContentScheduleInfo(
                                    modifier = Modifier.fillMaxWidth(),
                                    leadingText = "시작",
                                    dateTimeInfo = state.startDateTimeInfo.dateTime,
                                    onClickDate = { onIntent(ContentCreateIntent.ClickDate(type = ScheduleDateTimeType.START)) },
                                    onClickTime = { onIntent(ContentCreateIntent.ClickTime(type = ScheduleDateTimeType.START)) },
                                    isAllDay = state.isAllDay,
                                )

                                ContentScheduleInfo(
                                    modifier = Modifier.fillMaxWidth(),
                                    leadingText = "종료",
                                    dateTimeInfo = state.endDateTimeInfo.dateTime,
                                    onClickDate = { onIntent(ContentCreateIntent.ClickDate(type = ScheduleDateTimeType.END)) },
                                    onClickTime = { onIntent(ContentCreateIntent.ClickTime(type = ScheduleDateTimeType.END)) },
                                    isAllDay = state.isAllDay,
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier =
                                Modifier.padding(
                                    vertical = CaramelTheme.spacing.m,
                                ),
                            color = CaramelTheme.color.divider.primary,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(
                                    space = CaramelTheme.spacing.l,
                                    alignment = Alignment.CenterHorizontally,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_tag_18),
                                contentDescription = null,
                            )
                            SelectableTagChipRow(
                                modifier = Modifier.fillMaxWidth(),
                                tagChips =
                                    state.tags
                                        .map { TagChip(it.id, it.label) }
                                        .toImmutableList(),
                                selectedTagChips =
                                    state.selectedTags
                                        .map { TagChip(it.id, it.label) }
                                        .toImmutableList(),
                                onTagChipClick = {
                                    onIntent(ContentCreateIntent.ClickTag(Tag(it.id, it.label)))
                                },
                            )
                        }

                        Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.xl))
                    }
                }
            }
        }
    }
    if (state.showDateDialog || state.showTimeDialog) {
        DateBottomSheet(
            sheetState = sheetState,
            onDismiss = { onIntent(ContentCreateIntent.HideDateTimeDialog) },
            content = {
                when {
                    state.showDateDialog -> {
                        CaramelDatePicker(
                            modifier =
                                Modifier
                                    .padding(top = CaramelTheme.spacing.xxl)
                                    .align(Alignment.CenterHorizontally),
                            dateUiState = state.recentDateTimeInfo.dateUiState,
                            onYearChanged = { year ->
                                onIntent(ContentCreateIntent.OnYearChanged(year))
                            },
                            onDayChanged = { day ->
                                onIntent(ContentCreateIntent.OnDayChanged(day))
                            },
                            onMonthChanged = { month ->
                                onIntent(ContentCreateIntent.OnMonthChanged(month))
                            },
                        )
                    }

                    state.showTimeDialog -> {
                        CaramelTimePicker(
                            modifier =
                                Modifier
                                    .padding(top = CaramelTheme.spacing.xxl)
                                    .align(Alignment.CenterHorizontally),
                            timeUiState = state.recentDateTimeInfo.timeUiState,
                            onPeriodChanged = { period ->
                                onIntent(ContentCreateIntent.OnPeriodChanged(period))
                            },
                            onHourChanged = { hour ->
                                onIntent(ContentCreateIntent.OnHourChanged(hour))
                            },
                            onMinuteChanged = { minute ->
                                onIntent(ContentCreateIntent.OnMinuteChanged(minute))
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.l))
                CaramelButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = CaramelTheme.spacing.xl)
                            .padding(bottom = CaramelTheme.spacing.xl),
                    buttonType = CaramelButtonType.Enabled1,
                    buttonSize = CaramelButtonSize.Large,
                    text = "완료",
                    onClick = { onIntent(ContentCreateIntent.ClickCompleteButton) },
                )
            },
        )
    }
}

@Preview
@Composable
private fun ContentCreateScreenPreview() {
    CaramelTheme {
        ContentScreen(
            state =
                ContentCreateState(
                    createMode = CreateMode.CALENDAR,
                    tags =
                        (0L..5L)
                            .map {
                                Tag(
                                    id = it,
                                    label = "asd",
                                )
                            }.toImmutableList(),
                ),
            onIntent = {},
        )
    }
}
