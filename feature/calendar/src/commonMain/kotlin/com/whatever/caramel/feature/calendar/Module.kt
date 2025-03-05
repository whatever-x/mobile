package com.whatever.caramel.feature.calendar

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val calendarFeatureModule = module {
    viewModelOf(::CalendarViewModel)
}