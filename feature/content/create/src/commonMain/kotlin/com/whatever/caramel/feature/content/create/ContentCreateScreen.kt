@file:OptIn(ExperimentalMaterial3Api::class)

package com.whatever.caramel.feature.content.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.ic_check_14
import com.whatever.caramel.core.designsystem.components.CaramelButton
import com.whatever.caramel.core.designsystem.components.CaramelButtonSize
import com.whatever.caramel.core.designsystem.components.CaramelButtonType
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.ui.content.ContentAssigneeChipRow
import com.whatever.caramel.core.ui.content.ContentScheduleInfo
import com.whatever.caramel.core.ui.content.ContentTextArea
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.content.CreateModeSwitch
import com.whatever.caramel.core.ui.content.DateBottomSheet
import com.whatever.caramel.core.ui.content.SelectableTagChipRow
import com.whatever.caramel.core.ui.content.TagChip
import com.whatever.caramel.core.ui.content.TitleTextField
import com.whatever.caramel.core.ui.picker.CaramelDatePicker
import com.whatever.caramel.core.ui.picker.CaramelTimePicker
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val contentScrollState = rememberScrollState()
    val contentTextScrollState = rememberScrollState()
    var contentHeight by remember { mutableStateOf(0.dp) }
    LaunchedEffect(state.createMode) {
        if (state.createMode == CreateMode.MEMO) {
            contentHeight = 0.dp
        }
    }
    val density = LocalDensity.current

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .pointerInput(Unit) {
                    detectTapGestures { keyboardController?.hide() }
                },
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            Column {
                CaramelTopBar(
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
            }
        },
        bottomBar = {
            CaramelButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = CaramelTheme.color.background.primary)
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
                    .padding(contentPadding)
                    .fillMaxSize()
                    .padding(horizontal = CaramelTheme.spacing.xl)
                    .verticalScroll(contentScrollState),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                TitleTextField(
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
                        Modifier.padding(vertical = CaramelTheme.spacing.xl),
                    color = CaramelTheme.color.divider.primary,
                )
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 300.dp)
                        .onGloballyPositioned { coordinates ->
                            contentHeight = with(density) { coordinates.size.height.toDp() }
                        }.then(
                            if (contentHeight == 0.dp) {
                                Modifier.weight(1f)
                            } else {
                                Modifier.height(
                                    contentHeight,
                                )
                            },
                        ).verticalScroll(contentTextScrollState),
            ) {
                ContentTextArea(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    value = state.content,
                    onValueChange = {
                        onIntent(ContentCreateIntent.InputContent(it))
                    },
                    focusRequester = contentFocusRequester,
                    placeholder = "함께 하고 싶거나 기억하면 좋은 것들을\n자유롭게 입력해 주세요.",
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                HorizontalDivider(
                    modifier = Modifier.padding(bottom = CaramelTheme.spacing.l),
                    color = CaramelTheme.color.divider.primary,
                )
                ContentAssigneeChipRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedAssigneeChip = state.selectedAssignee,
                    onAssigneeChipClick = { assignee ->
                        onIntent(ContentCreateIntent.ClickAssignee(assignee = assignee))
                    },
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = CaramelTheme.spacing.m),
                    color = CaramelTheme.color.divider.primary,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CreateModeSwitch(
                        createMode = state.createMode,
                        onCreateModeSelect = {
                            onIntent(ContentCreateIntent.SelectCreateMode(it))
                        },
                    )
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
                            onClickDate = {
                                onIntent(
                                    ContentCreateIntent.ClickDate(
                                        type = ScheduleDateTimeType.START,
                                    ),
                                )
                            },
                            onClickTime = {
                                onIntent(
                                    ContentCreateIntent.ClickTime(
                                        type = ScheduleDateTimeType.START,
                                    ),
                                )
                            },
                            isAllDay = state.isAllDay,
                        )

                        ContentScheduleInfo(
                            modifier = Modifier.fillMaxWidth(),
                            leadingText = "종료",
                            dateTimeInfo = state.endDateTimeInfo.dateTime,
                            onClickDate = {
                                onIntent(
                                    ContentCreateIntent.ClickDate(
                                        type = ScheduleDateTimeType.END,
                                    ),
                                )
                            },
                            onClickTime = {
                                onIntent(
                                    ContentCreateIntent.ClickTime(
                                        type = ScheduleDateTimeType.END,
                                    ),
                                )
                            },
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
                SelectableTagChipRow(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(height = 36.dp),
                    tagChips =
                        state.tags
                            .map { TagChip(it.id, it.label) }
                            .toImmutableList(),
                    selectedTagChips =
                        state.selectedTags
                            .map { TagChip(it.id, it.label) }
                            .toImmutableList(),
                    onTagChipClick = {
                        onIntent(
                            ContentCreateIntent.ClickTag(
                                Tag(
                                    it.id,
                                    it.label,
                                ),
                            ),
                        )
                    },
                )
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
                                dateUiState = state.pickerDateTimeInfo.dateUiState,
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
                                timeUiState = state.pickerDateTimeInfo.timeUiState,
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
