package com.whatever.caramel.core.domain.di

import com.whatever.caramel.core.domain.usecase.auth.LogoutUseCase
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.domain.usecase.auth.SignOutUseCase
import com.whatever.caramel.core.domain.usecase.balanceGame.GetTodayBalanceGameUseCase
import com.whatever.caramel.core.domain.usecase.balanceGame.SubmitBalanceGameChoiceUseCase
import com.whatever.caramel.core.domain.usecase.calendar.DeleteScheduleUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetHolidaysUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetScheduleUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetTodayScheduleUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetTodosGroupByStartDateUseCase
import com.whatever.caramel.core.domain.usecase.calendar.UpdateScheduleUseCase
import com.whatever.caramel.core.domain.usecase.common.GetLinkPreviewsForContentUseCase
import com.whatever.caramel.core.domain.usecase.content.GetMemosUseCase
import com.whatever.caramel.core.domain.usecase.couple.ConnectCoupleUseCase
import com.whatever.caramel.core.domain.usecase.couple.EditCoupleStartDateUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetAnniversariesUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInfoUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInvitationCodeUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleRelationshipInfoUseCase
import com.whatever.caramel.core.domain.usecase.couple.UpdateShareMessageUseCase
import com.whatever.caramel.core.domain.usecase.memo.CreateContentUseCase
import com.whatever.caramel.core.domain.usecase.memo.DeleteMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.GetMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.UpdateMemoUseCase
import com.whatever.caramel.core.domain.usecase.tag.GetTagUseCase
import com.whatever.caramel.core.domain.usecase.user.CreateUserProfileUseCase
import com.whatever.caramel.core.domain.usecase.user.EditProfileUseCase
import com.whatever.caramel.core.domain.usecase.user.GetUserSettingUseCase
import com.whatever.caramel.core.domain.usecase.user.GetUserStatusUseCase
import com.whatever.caramel.core.domain.usecase.user.RefreshUserSessionUseCase
import com.whatever.caramel.core.domain.usecase.user.UpdateUserSettingUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        // Auth
        factory { SignInWithSocialPlatformUseCase(get(), get(), get()) }
        factory { RefreshUserSessionUseCase(get(), get()) }
        factory { LogoutUseCase(get(), get(), get()) }
        factory { SignOutUseCase(get(), get(), get()) }

        // User
        factory { CreateUserProfileUseCase(get()) }
        factory { EditProfileUseCase(get()) }
        factory { GetUserStatusUseCase(get()) }
        factory { UpdateUserSettingUseCase(get()) }
        factory { GetUserSettingUseCase(get()) }

        // Couple
        factory { GetCoupleInvitationCodeUseCase(get()) }
        factory { ConnectCoupleUseCase(get(), get()) }
        factory { GetCoupleRelationshipInfoUseCase(get()) }
        factory { EditCoupleStartDateUseCase(get()) }
        factory { UpdateShareMessageUseCase(get()) }
        factory { GetCoupleInfoUseCase(get()) }
        factory { GetAnniversariesUseCase(get()) }

        // Calender
        factory { GetTodosGroupByStartDateUseCase(get()) }
        factory { GetHolidaysUseCase(get()) }
        factory { GetTodayScheduleUseCase(get()) }
        factory { UpdateScheduleUseCase(get()) }
        factory { DeleteScheduleUseCase(get()) }
        factory { GetScheduleUseCase(get()) }

        // Tag
        factory { GetTagUseCase(get()) }

        // Content
        factory { CreateContentUseCase(get(), get()) }
        factory { UpdateMemoUseCase(get()) }
        factory { DeleteMemoUseCase(get()) }
        factory { GetMemosUseCase(get()) }
        factory { GetMemoUseCase(get()) }

        // BalanceGame
        factory { GetTodayBalanceGameUseCase(get()) }
        factory { SubmitBalanceGameChoiceUseCase(get()) }

        // Common
        factory { GetLinkPreviewsForContentUseCase(get()) }
    }
