@file:OptIn(ExperimentalMaterial3Api::class)

package com.whatever.caramel.feature.content.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.whatever.caramel.feature.content.edit.mvi.ContentEditIntent
import com.whatever.caramel.feature.content.edit.mvi.ContentEditState
import com.whatever.caramel.feature.content.edit.mvi.ScheduleDateTimeType
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContentEditScreen(
    state: ContentEditState,
    onIntent: (ContentEditIntent) -> Unit,
) {
    val contentFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val sheetState =
        rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    LaunchedEffect(state.showDateDialog, state.showTimeDialog) {
        if (state.showDateDialog || state.showTimeDialog) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }
    var contentHeight by remember { mutableStateOf(0.dp) }
    LaunchedEffect(state.createMode) {
        if (state.createMode == CreateMode.MEMO) {
            contentHeight = 0.dp
        }
    }
    val contentScrollState = rememberScrollState()
    val contentTextScrollState = rememberScrollState()
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
            CaramelTopBar(
                trailingIcon = {
                    Icon(
                        modifier =
                            Modifier.clickable {
                                onIntent(ContentEditIntent.OnBackClicked)
                            },
                        painter = painterResource(resource = Resources.Icon.ic_cancel_24),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = "Close",
                    )
                },
            )
        },
        bottomBar = {
            CaramelButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = CaramelTheme.color.background.primary)
                        .imePadding()
                        .padding(all = CaramelTheme.spacing.xl),
                onClick = { onIntent(ContentEditIntent.OnSaveClicked) },
                text = "저장",
                buttonType = if (state.isSaveButtonEnable) CaramelButtonType.Enabled1 else CaramelButtonType.Disabled,
                buttonSize = CaramelButtonSize.Large,
            )
        },
    ) { contentPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(horizontal = CaramelTheme.spacing.xl)
                    .verticalScroll(contentScrollState),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                TitleTextField(
                    value = state.title,
                    onValueChange = { onIntent(ContentEditIntent.OnTitleChanged(it)) },
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
                    modifier = Modifier.fillMaxWidth(),
                    value = state.content,
                    onValueChange = { onIntent(ContentEditIntent.OnContentChanged(it)) },
                    focusRequester = contentFocusRequester,
                    placeholder = "함께 하고 싶거나 기억하면 좋은 것들을 자유롭게 입력해 주세요.",
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
                        onIntent(ContentEditIntent.ClickAssignee(assignee = assignee))
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
                            onIntent(ContentEditIntent.OnCreateModeSelected(it))
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
                                            onClick = { onIntent(ContentEditIntent.ClickAllDayButton) },
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
                                    ContentEditIntent.ClickDate(
                                        type = ScheduleDateTimeType.START,
                                    ),
                                )
                            },
                            onClickTime = {
                                onIntent(
                                    ContentEditIntent.ClickTime(
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
                                    ContentEditIntent.ClickDate(
                                        type = ScheduleDateTimeType.END,
                                    ),
                                )
                            },
                            onClickTime = {
                                onIntent(
                                    ContentEditIntent.ClickTime(
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
                            ContentEditIntent.ClickTag(
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
    }

    if (state.showDateDialog || state.showTimeDialog) {
        DateBottomSheet(
            sheetState = sheetState,
            onDismiss = { onIntent(ContentEditIntent.HideDateTimeDialog) },
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
                                onIntent(ContentEditIntent.OnYearChanged(year))
                            },
                            onMonthChanged = { month ->
                                onIntent(ContentEditIntent.OnMonthChanged(month))
                            },
                            onDayChanged = { day ->
                                onIntent(ContentEditIntent.OnDayChanged(day))
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
                                onIntent(
                                    ContentEditIntent.OnPeriodChanged(
                                        period,
                                    ),
                                )
                            },
                            onHourChanged = { hour -> onIntent(ContentEditIntent.OnHourChanged(hour)) },
                            onMinuteChanged = { minute ->
                                onIntent(
                                    ContentEditIntent.OnMinuteChanged(
                                        minute,
                                    ),
                                )
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.l))
                CaramelButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(all = CaramelTheme.spacing.xl),
                    buttonType = CaramelButtonType.Enabled1,
                    buttonSize = CaramelButtonSize.Large,
                    text = "완료",
                    onClick = { onIntent(ContentEditIntent.ClickCompleteButton) },
                )
            },
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
                keyboardVisible.value = bottom > 0
            }
    }
    return keyboardVisible
}
