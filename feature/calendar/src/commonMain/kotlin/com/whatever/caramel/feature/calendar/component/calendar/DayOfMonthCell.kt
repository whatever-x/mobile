package com.whatever.caramel.feature.calendar.component.calendar

//@Composable
//internal fun CalendarDayOfMonthCell(
//    modifier: Modifier = Modifier,
//    schedule: DaySchedule?,
//    date: LocalDate,
//    isFocus: Boolean,
//    onClickCell: (LocalDate) -> Unit = {},
//    onClickSchedule: (Long) -> Unit = {},
//) {
//    Box(modifier = modifier) {
//        Column(
//            modifier =
//                Modifier
//                    .fillMaxSize()
//                    .clickable(
//                        indication = null,
//                        interactionSource = null,
//                        onClick = { onClickCell(date) },
//                    ),
//            verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.xxs),
//        ) {
//            CalendarDayOfMonthText(
//                modifier = Modifier.fillMaxWidth(),
//                dayOfWeek = date.dayOfWeek,
//                dayOfMonth = date.dayOfMonth,
//                isFocus = isFocus,
//                isHoliday = schedule?.holidayList?.isNotEmpty() ?: false,
//            )
//            if (schedule != null) {
//                CalendarScheduleList(
//                    schedule = schedule,
//                    onClickSchedule = { onClickSchedule(it) },
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun CalendarDayOfMonthText(
//    modifier: Modifier = Modifier,
//    dayOfWeek: DayOfWeek,
//    dayOfMonth: Int,
//    isFocus: Boolean,
//    isHoliday: Boolean,
//) {
//    Box(modifier = modifier) {
//        Box(
//            modifier =
//                Modifier
//                    .background(
//                        color =
//                            if (isFocus) {
//                                CaramelTheme.color.fill.primary
//                            } else {
//                                Color.Unspecified
//                            },
//                        shape = CaramelTheme.shape.s,
//                    ).align(Alignment.Center)
//                    .size(24.dp),
//        )
//
//        Text(
//            modifier =
//                Modifier
//                    .align(Alignment.Center),
//            text = dayOfMonth.toString(),
//            style = CaramelTheme.typography.body4.regular,
//            color =
//                when {
//                    isFocus -> CaramelTheme.color.text.inverse
//                    dayOfWeek == DayOfWeek.SATURDAY -> CaramelTheme.color.text.labelAccent2
//                    isHoliday || dayOfWeek == DayOfWeek.SUNDAY -> CaramelTheme.color.text.labelAccent1
//                    else -> CaramelTheme.color.text.primary
//                },
//        )
//    }
//}
