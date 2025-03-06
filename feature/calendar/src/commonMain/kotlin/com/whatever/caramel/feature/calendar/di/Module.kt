package com.whatever.caramel.feature.calendar.di

import com.whatever.caramel.feature.calendar.CalendarViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val calendarFeatureModule = module {
    viewModelOf(::CalendarViewModel)
}