# 밸런스 게임 히스토리 페이지 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 밸런스 게임 히스토리 화면을 구현한다 — 서버 커서 페이징으로 과거 결과를 `LazyColumn`에 표시하고, 카드 탭 시 아코디언 애니메이션으로 펼쳐지며, "결과 공유하기"로 결과 전체 데이터를 들고 공유 페이지로 이동한다.

**Architecture:** KMP + Compose Multiplatform, MVI. 의존성은 아래로만 향한다(`:feature` → `:core:domain`, 데이터는 use case 경유). 커서 페이징은 메모(`MemoWithCursor`/`CursoredContentResponse`)와 동일한 패턴을 미러링한다. 프로필 렌더링은 홈 `BalanceGameResult`(`Gender`→기본 에셋, 닉네임은 커플 정보)를 재사용한다.

**Tech Stack:** Kotlin Multiplatform · Compose Multiplatform · Koin · Ktor · kotlinx-serialization · kotlinx-datetime · kotlinx-collections-immutable · Compose Navigation(type-safe routes)

---

## 검증 방식에 대한 참고 (TDD 예외)

대상 모듈(`:core:domain`, `:core:data`, `:core:remote`, `:feature:balancegame`)에는 테스트 인프라(`caramel.kmp.test` 플러그인 / `commonTest` 소스셋)가 **없다**. 테스트 인프라 신설은 이 작업 범위를 벗어나므로(범위 외 수정 금지), 본 계획은 각 태스크를 **컴파일 + spotless + 빌드** 로 검증한다. 순수 로직(날짜 포맷, 아코디언 토글)은 구현 단순성으로 정확성을 보장한다.

공통 검증 명령(자주 사용):
- 포맷: `./gradlew spotlessApply`
- 빌드(피처): `./gradlew :feature:balancegame:compileDebugKotlinAndroid`
- 빌드(전체 앱): `./gradlew :app:assembleDebug`

모든 `git` 명령과 `./gradlew`는 워크트리 루트 `/Users/ricky/Documents/workspace/mobile/.claude/worktrees/balance-game-history` 에서 실행한다.

---

## File Structure

**`:core:domain`**
- Create `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/vo/balanceGame/BalanceGameHistory.kt`
- Modify `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/repository/BalanceGameRepository.kt`
- Create `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/usecase/balanceGame/GetBalanceGameHistoryUseCase.kt`
- Modify `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/di/Module.kt`

**`:core:remote`**
- Create `core/remote/src/commonMain/kotlin/com/whatever/caramel/core/remote/dto/balanceGame/response/BalanceGameHistoryResponse.kt`
- Modify `core/remote/src/commonMain/kotlin/com/whatever/caramel/core/remote/datasource/RemoteBalanceGameDataSource.kt`
- Modify `core/remote/src/commonMain/kotlin/com/whatever/caramel/core/remote/datasource/RemoteBalanceGameDataSourceImpl.kt`

**`:core:data`**
- Modify `core/data/src/commonMain/kotlin/com/whatever/caramel/core/data/mapper/BalanceGameMapper.kt`
- Modify `core/data/src/commonMain/kotlin/com/whatever/caramel/core/data/repository/BalanceGameRepositoryImpl.kt`

**`:feature:balancegame` (history)**
- Create `.../history/model/BalanceGameHistoryUiModel.kt`
- Modify `.../history/mvi/BalanceGameHistoryState.kt`
- Modify `.../history/mvi/BalanceGameHistoryIntent.kt`
- Modify `.../history/mvi/BalanceGameHistorySideEffect.kt`
- Modify `.../history/BalanceGameHistoryViewModel.kt`
- Create `.../history/components/BalanceGameHistoryCard.kt`
- Modify `.../history/BalanceGameHistoryScreen.kt`
- Modify `.../history/BalanceGameHistoryRoute.kt`
- Modify `.../history/navigation/BalanceGameHistoryNavigation.kt`
- Modify `feature/balancegame/src/commonMain/composeResources/values/strings.xml`

**`:feature:balancegame` (share) + app + home (공유 라우트 데이터 전달 파급)**
- Modify `.../share/navigation/BalanceGameShareNavigation.kt`
- Modify `.../share/mvi/BalanceGameShareState.kt`
- Modify `.../share/BalanceGameShareViewModel.kt`
- Modify `app/src/commonMain/kotlin/com/whatever/caramel/app/CaramelNavHost.kt`
- Modify `feature/home/.../mvi/HomeSideEffect.kt`
- Modify `feature/home/.../HomeViewModel.kt`
- Modify `feature/home/.../HomeRoute.kt`
- Modify `feature/home/.../navigation/HomeNavigation.kt`
- Modify `feature/main/.../MainRoute.kt`
- Modify `feature/main/.../navigation/MainNavigation.kt`

피처 모듈 경로 접두사(이하 `FB`):
`feature/balancegame/src/commonMain/kotlin/com/whatever/caramel/feature/balancegame`

---

## Task 1: 도메인 — 히스토리 VO·리포지토리·유즈케이스·DI

**Files:**
- Create: `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/vo/balanceGame/BalanceGameHistory.kt`
- Modify: `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/repository/BalanceGameRepository.kt`
- Create: `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/usecase/balanceGame/GetBalanceGameHistoryUseCase.kt`
- Modify: `core/domain/src/commonMain/kotlin/com/whatever/caramel/core/domain/di/Module.kt:92-94`

- [ ] **Step 1: VO 생성**

`BalanceGameHistory.kt`:
```kotlin
package com.whatever.caramel.core.domain.vo.balanceGame

data class BalanceGameHistory(
    val nextCursor: String?,
    val gameResults: List<BalanceGameResult>,
)
```

- [ ] **Step 2: 리포지토리 인터페이스에 메서드 추가**

`BalanceGameRepository.kt` 에 import 추가 및 메서드 추가:
```kotlin
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameHistory
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

interface BalanceGameRepository {
    suspend fun getTodayBalanceGame(): BalanceGameResult

    suspend fun submitOption(
        gameId: Long,
        optionId: Long,
    ): BalanceGameResult

    suspend fun getBalanceGameHistory(
        size: Int?,
        cursor: String?,
    ): BalanceGameHistory
}
```

- [ ] **Step 3: 유즈케이스 생성**

`GetBalanceGameHistoryUseCase.kt`:
```kotlin
package com.whatever.caramel.core.domain.usecase.balanceGame

import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameHistory

class GetBalanceGameHistoryUseCase(
    private val balanceGameRepository: BalanceGameRepository,
) {
    suspend operator fun invoke(
        size: Int? = null,
        cursor: String? = null,
    ): BalanceGameHistory =
        balanceGameRepository.getBalanceGameHistory(
            size = size,
            cursor = cursor,
        )
}
```

- [ ] **Step 4: DI 등록**

`core/domain/.../di/Module.kt` 의 `// BalanceGame` 블록에 import와 factory 추가:
```kotlin
import com.whatever.caramel.core.domain.usecase.balanceGame.GetBalanceGameHistoryUseCase
```
```kotlin
        // BalanceGame
        factory { GetTodayBalanceGameUseCase(get()) }
        factory { SubmitBalanceGameChoiceUseCase(get(), get()) }
        factory { GetBalanceGameHistoryUseCase(get()) }
```

- [ ] **Step 5: 컴파일 검증**

Run: `./gradlew :core:domain:compileKotlinJvm`
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: 커밋**

```bash
git add core/domain
git commit -m "[Feat] 밸런스 게임 히스토리 도메인 VO/유즈케이스 추가"
```

---

## Task 2: 리모트 — 히스토리 응답 DTO·데이터소스

**Files:**
- Create: `core/remote/src/commonMain/kotlin/com/whatever/caramel/core/remote/dto/balanceGame/response/BalanceGameHistoryResponse.kt`
- Modify: `core/remote/src/commonMain/kotlin/com/whatever/caramel/core/remote/datasource/RemoteBalanceGameDataSource.kt`
- Modify: `core/remote/src/commonMain/kotlin/com/whatever/caramel/core/remote/datasource/RemoteBalanceGameDataSourceImpl.kt`

- [ ] **Step 1: 응답 DTO 생성**

`BalanceGameHistoryResponse.kt` (메모 `CursoredContentResponse` 구조 미러, 항목은 기존 `BalanceGameResponse` 재사용):
```kotlin
package com.whatever.caramel.core.remote.dto.balanceGame.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceGameHistoryResponse(
    @SerialName("list") val list: List<BalanceGameResponse>,
    @SerialName("cursor") val cursor: BalanceGameHistoryCursor,
)

@Serializable
data class BalanceGameHistoryCursor(
    @SerialName("next") val next: String?,
)
```

- [ ] **Step 2: 데이터소스 인터페이스에 메서드 추가**

`RemoteBalanceGameDataSource.kt`:
```kotlin
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameHistoryResponse
```
```kotlin
    suspend fun fetchBalanceGameHistory(
        size: Int?,
        cursor: String?,
    ): BalanceGameHistoryResponse
```

- [ ] **Step 3: 데이터소스 구현 추가**

`RemoteBalanceGameDataSourceImpl.kt` 에 import 추가:
```kotlin
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameHistoryResponse
import io.ktor.client.request.parameter
```
`sendChooseOption` 아래에 메서드 추가:
```kotlin
    override suspend fun fetchBalanceGameHistory(
        size: Int?,
        cursor: String?,
    ): BalanceGameHistoryResponse =
        authClient
            .get(BALANCE_GAME_BASE_URL) {
                size?.let { parameter("size", it) }
                cursor?.let { parameter("cursor", it) }
            }.getBody()
```
> ⚠️ 엔드포인트(`GET /v1/balance-game`)와 응답 형식은 추정값. 백엔드 확정 시 이 메서드와 DTO·매퍼만 수정.

- [ ] **Step 4: 컴파일 검증**

Run: `./gradlew :core:remote:compileKotlinJvm`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 커밋**

```bash
git add core/remote
git commit -m "[Feat] 밸런스 게임 히스토리 응답 DTO/데이터소스 추가"
```

---

## Task 3: 데이터 — 매퍼·리포지토리 구현

**Files:**
- Modify: `core/data/src/commonMain/kotlin/com/whatever/caramel/core/data/mapper/BalanceGameMapper.kt`
- Modify: `core/data/src/commonMain/kotlin/com/whatever/caramel/core/data/repository/BalanceGameRepositoryImpl.kt`

- [ ] **Step 1: 매퍼 추가**

`BalanceGameMapper.kt` 에 import 추가:
```kotlin
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameHistory
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameHistoryResponse
```
파일 끝에 함수 추가(기존 `toBalanceGameResult()` 재사용):
```kotlin
internal fun BalanceGameHistoryResponse.toBalanceGameHistory(): BalanceGameHistory =
    BalanceGameHistory(
        nextCursor = this.cursor.next,
        gameResults = this.list.map { it.toBalanceGameResult() },
    )
```

- [ ] **Step 2: 리포지토리 구현 추가**

`BalanceGameRepositoryImpl.kt` 에 import 추가:
```kotlin
import com.whatever.caramel.core.data.mapper.toBalanceGameHistory
import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameHistory
```
`submitOption` 아래에 메서드 추가:
```kotlin
    override suspend fun getBalanceGameHistory(
        size: Int?,
        cursor: String?,
    ): BalanceGameHistory =
        safeCall {
            remoteBalanceGameDataSource
                .fetchBalanceGameHistory(
                    size = size,
                    cursor = cursor,
                ).toBalanceGameHistory()
        }
```

- [ ] **Step 3: 컴파일 검증**

Run: `./gradlew :core:data:compileKotlinJvm`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 커밋**

```bash
git add core/data
git commit -m "[Feat] 밸런스 게임 히스토리 매퍼/리포지토리 구현"
```

---

## Task 4: 피처 — 히스토리 UI 모델·매퍼

**Files:**
- Create: `FB/history/model/BalanceGameHistoryUiModel.kt`

- [ ] **Step 1: UI 모델 + 매퍼 생성**

`BalanceGameHistoryUiModel.kt`:
```kotlin
package com.whatever.caramel.feature.balancegame.history.model

import com.whatever.caramel.core.domain.vo.balanceGame.BalanceGameResult

data class BalanceGameHistoryUiModel(
    val gameId: Long,
    val question: String,
    val dateText: String,
    val myChoiceText: String,
    val partnerChoiceText: String,
)

internal fun BalanceGameResult.toHistoryUiModel(): BalanceGameHistoryUiModel =
    BalanceGameHistoryUiModel(
        gameId = gameInfo.id,
        question = gameInfo.question,
        dateText =
            buildString {
                append(gameInfo.date.year.toString())
                append(".")
                append(gameInfo.date.monthNumber.toString().padStart(2, '0'))
                append(".")
                append(gameInfo.date.dayOfMonth.toString().padStart(2, '0'))
            },
        myChoiceText = myChoice?.text ?: "",
        partnerChoiceText = partnerChoice?.text ?: "",
    )
```
> `LocalDate`의 `year`/`monthNumber`/`dayOfMonth`는 kotlinx-datetime 표준 프로퍼티. core:util 의존 없이 인라인 포맷.

- [ ] **Step 2: 컴파일 검증**

Run: `./gradlew :feature:balancegame:compileDebugKotlinAndroid`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 커밋**

```bash
git add feature/balancegame
git commit -m "[Feat] 밸런스 게임 히스토리 UI 모델/매퍼 추가"
```

---

## Task 5: 피처 — MVI(State/Intent/SideEffect)

**Files:**
- Modify: `FB/history/mvi/BalanceGameHistoryState.kt`
- Modify: `FB/history/mvi/BalanceGameHistoryIntent.kt`
- Modify: `FB/history/mvi/BalanceGameHistorySideEffect.kt`

- [ ] **Step 1: State 작성**

`BalanceGameHistoryState.kt` 전체 교체:
```kotlin
package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.UiState
import com.whatever.caramel.feature.balancegame.history.model.BalanceGameHistoryUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BalanceGameHistoryState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isEndReached: Boolean = false,
    val nextCursor: String? = null,
    val myNickname: String = "",
    val myGender: Gender = Gender.IDLE,
    val partnerNickname: String = "",
    val partnerGender: Gender = Gender.IDLE,
    val items: ImmutableList<BalanceGameHistoryUiModel> = persistentListOf(),
    val expandedGameId: Long? = null,
) : UiState
```

- [ ] **Step 2: Intent 작성**

`BalanceGameHistoryIntent.kt` 전체 교체:
```kotlin
package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface BalanceGameHistoryIntent : UiIntent {
    data object ClickBackButton : BalanceGameHistoryIntent

    data class ClickHistoryCard(
        val gameId: Long,
    ) : BalanceGameHistoryIntent

    data class ClickShareResult(
        val gameId: Long,
    ) : BalanceGameHistoryIntent

    data object LoadMore : BalanceGameHistoryIntent
}
```

- [ ] **Step 3: SideEffect 작성**

`BalanceGameHistorySideEffect.kt` 전체 교체:
```kotlin
package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface BalanceGameHistorySideEffect : UiSideEffect {
    data object NavigateToBack : BalanceGameHistorySideEffect

    data class NavigateToShare(
        val gameId: Long,
        val question: String,
        val myNickname: String,
        val myGender: String,
        val myChoice: String,
        val partnerNickname: String,
        val partnerGender: String,
        val partnerChoice: String,
    ) : BalanceGameHistorySideEffect
}
```

- [ ] **Step 4: 컴파일 검증** (ViewModel은 아직 미수정이라 실패 가능 — 다음 태스크와 함께 검증)

Run: `./gradlew :feature:balancegame:compileDebugKotlinAndroid`
Expected: `BalanceGameHistoryViewModel`의 `handleIntent` when 절 미완 또는 미사용 import로 실패할 수 있음. Task 6에서 함께 통과시킨다. (이 단계 단독 커밋은 Task 6과 합쳐 진행)

> NOTE: Task 5와 Task 6은 ViewModel이 새 Intent/State를 참조하므로 함께 컴파일·커밋한다.

---

## Task 6: 피처 — ViewModel(커플 정보 + 페이징 + 아코디언 + 공유)

**Files:**
- Modify: `FB/history/BalanceGameHistoryViewModel.kt`
- Modify: `FB/di/Module.kt`

- [ ] **Step 1: ViewModel 전체 교체**

`BalanceGameHistoryViewModel.kt`:
```kotlin
package com.whatever.caramel.feature.balancegame.history

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.usecase.balanceGame.GetBalanceGameHistoryUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleRelationshipInfoUseCase
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.balancegame.history.model.toHistoryUiModel
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryIntent
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistorySideEffect
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryState
import kotlinx.collections.immutable.toImmutableList

class BalanceGameHistoryViewModel(
    private val getBalanceGameHistoryUseCase: GetBalanceGameHistoryUseCase,
    private val getCoupleRelationshipInfoUseCase: GetCoupleRelationshipInfoUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<BalanceGameHistoryState, BalanceGameHistorySideEffect, BalanceGameHistoryIntent>(savedStateHandle, crashlytics) {
    init {
        loadInitialData()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): BalanceGameHistoryState = BalanceGameHistoryState()

    override suspend fun handleIntent(intent: BalanceGameHistoryIntent) {
        when (intent) {
            is BalanceGameHistoryIntent.ClickBackButton -> postSideEffect(BalanceGameHistorySideEffect.NavigateToBack)
            is BalanceGameHistoryIntent.ClickHistoryCard -> toggleCard(gameId = intent.gameId)
            is BalanceGameHistoryIntent.ClickShareResult -> shareResult(gameId = intent.gameId)
            is BalanceGameHistoryIntent.LoadMore -> loadMore()
        }
    }

    private fun loadInitialData() {
        launch {
            reduce { copy(isLoading = true) }
            val coupleJob =
                launch {
                    val couple = getCoupleRelationshipInfoUseCase()
                    reduce {
                        copy(
                            myNickname = couple.myInfo.userProfile.nickName,
                            myGender = couple.myInfo.userProfile.gender,
                            partnerNickname = couple.partnerInfo?.userProfile?.nickName ?: "",
                            partnerGender = couple.partnerInfo?.userProfile?.gender ?: Gender.IDLE,
                        )
                    }
                }
            val historyJob =
                launch {
                    val history = getBalanceGameHistoryUseCase(cursor = null)
                    reduce {
                        copy(
                            items = history.gameResults.map { it.toHistoryUiModel() }.toImmutableList(),
                            nextCursor = history.nextCursor,
                            isEndReached = history.nextCursor == null,
                        )
                    }
                }
            coupleJob.join()
            historyJob.join()
            reduce { copy(isLoading = false) }
        }
    }

    private fun loadMore() {
        if (currentState.isLoadingMore || currentState.isEndReached || currentState.nextCursor == null) return
        launch {
            reduce { copy(isLoadingMore = true) }
            val history = getBalanceGameHistoryUseCase(cursor = currentState.nextCursor)
            reduce {
                copy(
                    items = (items + history.gameResults.map { it.toHistoryUiModel() }).toImmutableList(),
                    nextCursor = history.nextCursor,
                    isEndReached = history.nextCursor == null,
                    isLoadingMore = false,
                )
            }
        }
    }

    private fun toggleCard(gameId: Long) {
        reduce {
            copy(expandedGameId = if (expandedGameId == gameId) null else gameId)
        }
    }

    private fun shareResult(gameId: Long) {
        val item = currentState.items.firstOrNull { it.gameId == gameId } ?: return
        postSideEffect(
            BalanceGameHistorySideEffect.NavigateToShare(
                gameId = item.gameId,
                question = item.question,
                myNickname = currentState.myNickname,
                myGender = currentState.myGender.name,
                myChoice = item.myChoiceText,
                partnerNickname = currentState.partnerNickname,
                partnerGender = currentState.partnerGender.name,
                partnerChoice = item.partnerChoiceText,
            ),
        )
    }
}
```
> 에러 처리: `BaseViewModel.launch`가 `coroutineExceptionHandler`로 크래시리틱스 로깅을 자동 연결한다. 사용자向 에러 다이얼로그/토스트는 이 화면 요구사항에 없으므로 추가하지 않는다(기존 빈 화면에도 없었음).

- [ ] **Step 2: Koin DI 갱신**

`FB/di/Module.kt` 의 `viewModelOf(::BalanceGameHistoryViewModel)` 는 생성자 의존성을 자동 주입하므로 **변경 불필요**. (use case들은 Task 1에서, `GetCoupleRelationshipInfoUseCase`는 기존 도메인 모듈에 이미 등록됨.) 변경 없음 확인만.

- [ ] **Step 3: 컴파일 검증 (Task 5 + 6)**

Run: `./gradlew :feature:balancegame:compileDebugKotlinAndroid`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: spotless**

Run: `./gradlew spotlessApply`

- [ ] **Step 5: 커밋 (Task 5 + 6)**

```bash
git add feature/balancegame
git commit -m "[Feat] 밸런스 게임 히스토리 MVI/뷰모델 구현"
```

---

## Task 7: 피처 — 히스토리 카드 컴포넌트(접힘/펼침/애니메이션)

**Files:**
- Create: `FB/history/components/BalanceGameHistoryCard.kt`

- [ ] **Step 1: 카드 컴포넌트 생성**

`BalanceGameHistoryCard.kt`:
```kotlin
package com.whatever.caramel.feature.balancegame.history.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.balance_game_history_share_result
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.balancegame.history.model.BalanceGameHistoryUiModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BalanceGameHistoryCard(
    item: BalanceGameHistoryUiModel,
    expanded: Boolean,
    myNickname: String,
    myGender: Gender,
    partnerNickname: String,
    partnerGender: Gender,
    onClickCard: () -> Unit,
    onClickShare: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = CaramelTheme.color.background.tertiary,
                    shape = RoundedCornerShape(16.dp),
                ).border(
                    width = 1.dp,
                    color = CaramelTheme.color.fill.quaternary,
                    shape = RoundedCornerShape(16.dp),
                ).clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClickCard,
                ),
    ) {
        Header(
            item = item,
            expanded = expanded,
        )

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            ExpandedResult(
                item = item,
                myNickname = myNickname,
                myGender = myGender,
                partnerNickname = partnerNickname,
                partnerGender = partnerGender,
                onClickShare = onClickShare,
            )
        }
    }
}

@Composable
private fun Header(
    item: BalanceGameHistoryUiModel,
    expanded: Boolean,
) {
    val rotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = CaramelTheme.spacing.l,
                    vertical = 14.dp,
                ),
        horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xs),
        ) {
            Text(
                text = item.question,
                style = CaramelTheme.typography.heading3,
                color = CaramelTheme.color.text.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.dateText,
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.secondary,
            )
        }
        Icon(
            modifier = Modifier.size(16.dp).rotate(rotation),
            painter = painterResource(resource = Resources.Icon.ic_arrow_down_16),
            tint = CaramelTheme.color.icon.primary,
            contentDescription = null,
        )
    }
}

@Composable
private fun ExpandedResult(
    item: BalanceGameHistoryUiModel,
    myNickname: String,
    myGender: Gender,
    partnerNickname: String,
    partnerGender: Gender,
    onClickShare: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    start = CaramelTheme.spacing.l,
                    end = CaramelTheme.spacing.l,
                    bottom = CaramelTheme.spacing.l,
                ),
        verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.l),
    ) {
        HorizontalDivider(color = CaramelTheme.color.divider.primary)

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.question,
            style = CaramelTheme.typography.heading2,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center,
        )

        ProfileRow(
            nickname = myNickname,
            gender = myGender,
            choiceText = item.myChoiceText,
        )
        ProfileRow(
            nickname = partnerNickname,
            gender = partnerGender,
            choiceText = item.partnerChoiceText,
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(
                        color = CaramelTheme.color.fill.quinary,
                        shape = RoundedCornerShape(16.dp),
                    ).clickable(onClick = onClickShare),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(resource = Resources.Icon.ic_share_16),
                tint = CaramelTheme.color.icon.primary,
                contentDescription = null,
            )
            Text(
                text = stringResource(resource = Res.string.balance_game_history_share_result),
                style = CaramelTheme.typography.body4.bold,
                color = CaramelTheme.color.text.primary,
            )
        }
    }
}

@Composable
private fun ProfileRow(
    nickname: String,
    gender: Gender,
    choiceText: String,
) {
    val genderImage =
        when (gender) {
            Gender.FEMALE -> Resources.Image.img_quiz_woman
            else -> Resources.Image.img_quiz_man
        }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(resource = genderImage),
            contentDescription = null,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xxs),
        ) {
            Text(
                text = nickname,
                style = CaramelTheme.typography.body4.regular,
                color = CaramelTheme.color.text.secondary,
            )
            Text(
                text = choiceText,
                style = CaramelTheme.typography.body1.bold,
                color = CaramelTheme.color.text.primary,
            )
        }
    }
}
```
> 검증할 디자인시스템 심볼: `CaramelTheme.color.background.tertiary`, `color.fill.quaternary`, `color.fill.quinary`, `color.divider.primary`, `color.icon.primary`, `color.text.primary/secondary`; `typography.heading2/heading3/body1.bold/body3.regular/body4.bold`; `spacing.l/m/xs/xxs`; `Resources.Icon.ic_arrow_down_16`, `ic_share_16`; `Resources.Image.img_quiz_man/img_quiz_woman`. **Step 2 빌드 실패 시 실제 토큰명을 `core/designsystem`에서 확인해 교정**한다(예: `spacing.xxs`, `typography.body3` 존재 여부).

- [ ] **Step 2: 컴파일 검증**

Run: `./gradlew :feature:balancegame:compileDebugKotlinAndroid`
Expected: BUILD SUCCESSFUL (실패 시 토큰명 교정 후 재시도)

- [ ] **Step 3: 커밋**

```bash
git add feature/balancegame
git commit -m "[Feat] 밸런스 게임 히스토리 카드 컴포넌트 추가"
```

---

## Task 8: 피처 — 히스토리 화면(LazyColumn + 페이징 트리거)

**Files:**
- Modify: `FB/history/BalanceGameHistoryScreen.kt`

- [ ] **Step 1: Screen 전체 교체**

`BalanceGameHistoryScreen.kt`:
```kotlin
package com.whatever.caramel.feature.balancegame.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.balance_game_history_title
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.balancegame.history.components.BalanceGameHistoryCard
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryIntent
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BalanceGameHistoryScreen(
    state: BalanceGameHistoryState,
    onIntent: (BalanceGameHistoryIntent) -> Unit,
) {
    val listState = rememberLazyListState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 1
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onIntent(BalanceGameHistoryIntent.LoadMore)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CaramelTheme.color.background.primary),
    ) {
        CaramelTopBar(
            modifier = Modifier.statusBarsPadding(),
            centerContents = {
                Text(
                    text = stringResource(resource = Res.string.balance_game_history_title),
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary,
                )
            },
            leadingContent = {
                Icon(
                    modifier =
                        Modifier.clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { onIntent(BalanceGameHistoryIntent.ClickBackButton) },
                        ),
                    painter = painterResource(resource = Resources.Icon.ic_arrow_left_24),
                    tint = CaramelTheme.color.icon.primary,
                    contentDescription = null,
                )
            },
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding =
                PaddingValues(
                    start = CaramelTheme.spacing.xl,
                    end = CaramelTheme.spacing.xl,
                    top = CaramelTheme.spacing.l,
                    bottom = CaramelTheme.spacing.xxl,
                ),
            verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.m),
        ) {
            items(
                items = state.items,
                key = { it.gameId },
                contentType = { "history_item" },
            ) { item ->
                BalanceGameHistoryCard(
                    item = item,
                    expanded = state.expandedGameId == item.gameId,
                    myNickname = state.myNickname,
                    myGender = state.myGender,
                    partnerNickname = state.partnerNickname,
                    partnerGender = state.partnerGender,
                    onClickCard = { onIntent(BalanceGameHistoryIntent.ClickHistoryCard(item.gameId)) },
                    onClickShare = { onIntent(BalanceGameHistoryIntent.ClickShareResult(item.gameId)) },
                )
            }

            if (state.isLoadingMore) {
                item(contentType = "loading") {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(CaramelTheme.spacing.l),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}
```
> `CaramelTopBar` 사용부는 기존 빈 화면 구현을 그대로 유지(검증된 시그니처). 디자인 토큰 `spacing.xl/xxl` 미존재 시 Step 2에서 교정.

- [ ] **Step 2: 컴파일 검증**

Run: `./gradlew :feature:balancegame:compileDebugKotlinAndroid`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 커밋**

```bash
git add feature/balancegame
git commit -m "[Feat] 밸런스 게임 히스토리 화면(LazyColumn/페이징) 구현"
```

---

## Task 9: 피처 — 히스토리 Route 배선

**Files:**
- Modify: `FB/history/BalanceGameHistoryRoute.kt`
- Modify: `FB/history/navigation/BalanceGameHistoryNavigation.kt`

- [ ] **Step 1: Route에 공유 네비게이션 람다 추가**

`BalanceGameHistoryRoute.kt` 전체 교체:
```kotlin
package com.whatever.caramel.feature.balancegame.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistorySideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BalanceGameHistoryRoute(
    viewModel: BalanceGameHistoryViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
    navigateToShare: (
        gameId: Long,
        question: String,
        myNickname: String,
        myGender: String,
        myChoice: String,
        partnerNickname: String,
        partnerGender: String,
        partnerChoice: String,
    ) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is BalanceGameHistorySideEffect.NavigateToBack -> navigateToBack()
                is BalanceGameHistorySideEffect.NavigateToShare ->
                    navigateToShare(
                        sideEffect.gameId,
                        sideEffect.question,
                        sideEffect.myNickname,
                        sideEffect.myGender,
                        sideEffect.myChoice,
                        sideEffect.partnerNickname,
                        sideEffect.partnerGender,
                        sideEffect.partnerChoice,
                    )
            }
        }
    }

    BalanceGameHistoryScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
```

- [ ] **Step 2: Navigation에 공유 람다 전달**

`BalanceGameHistoryNavigation.kt` 의 `balanceGameHistoryScreen` 전체 교체:
```kotlin
fun NavGraphBuilder.balanceGameHistoryScreen(
    navigateToBack: () -> Unit,
    navigateToShare: (
        gameId: Long,
        question: String,
        myNickname: String,
        myGender: String,
        myChoice: String,
        partnerNickname: String,
        partnerGender: String,
        partnerChoice: String,
    ) -> Unit,
) {
    composable<BalanceGameHistoryRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
    ) {
        BalanceGameHistoryRoute(
            navigateToBack = navigateToBack,
            navigateToShare = navigateToShare,
        )
    }
}
```

- [ ] **Step 3: 컴파일 검증** (CaramelNavHost 호출부 미수정으로 app은 실패 가능 — Task 10에서 함께 통과)

Run: `./gradlew :feature:balancegame:compileDebugKotlinAndroid`
Expected: BUILD SUCCESSFUL (feature 단독). app은 Task 10에서.

- [ ] **Step 4: 커밋**

```bash
git add feature/balancegame
git commit -m "[Feat] 밸런스 게임 히스토리 Route 공유 네비게이션 배선"
```

---

## Task 10: 공유 라우트 데이터화 + 앱/홈 호출부 갱신

**Files:**
- Modify: `FB/share/navigation/BalanceGameShareNavigation.kt`
- Modify: `FB/share/mvi/BalanceGameShareState.kt`
- Modify: `FB/share/BalanceGameShareViewModel.kt`
- Modify: `app/src/commonMain/kotlin/com/whatever/caramel/app/CaramelNavHost.kt`
- Modify: `feature/home/.../mvi/HomeSideEffect.kt`
- Modify: `feature/home/.../HomeViewModel.kt`
- Modify: `feature/home/.../HomeRoute.kt`
- Modify: `feature/home/.../navigation/HomeNavigation.kt`
- Modify: `feature/main/.../MainRoute.kt`
- Modify: `feature/main/.../navigation/MainNavigation.kt`

- [ ] **Step 1: 공유 라우트를 data class로 변경**

`BalanceGameShareNavigation.kt` 전체 교체:
```kotlin
package com.whatever.caramel.feature.balancegame.share.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.balancegame.share.BalanceGameShareRoute as BalanceGameShareScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data class BalanceGameShareRoute(
    val gameId: Long,
    val question: String,
    val myNickname: String,
    val myGender: String,
    val myChoice: String,
    val partnerNickname: String,
    val partnerGender: String,
    val partnerChoice: String,
)

fun NavController.navigateToBalanceGameShare(
    gameId: Long,
    question: String,
    myNickname: String,
    myGender: String,
    myChoice: String,
    partnerNickname: String,
    partnerGender: String,
    partnerChoice: String,
    navOptions: NavOptions? = null,
) {
    navigate(
        route =
            BalanceGameShareRoute(
                gameId = gameId,
                question = question,
                myNickname = myNickname,
                myGender = myGender,
                myChoice = myChoice,
                partnerNickname = partnerNickname,
                partnerGender = partnerGender,
                partnerChoice = partnerChoice,
            ),
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.balanceGameShareScreen(navigateToBack: () -> Unit) {
    composable<BalanceGameShareRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
    ) {
        BalanceGameShareScreenRoute(
            navigateToBack = navigateToBack,
        )
    }
}
```
> `BalanceGameShareRoute` 가 nav 라우트 클래스(navigation 패키지)와 컴포저블 함수(share 패키지)로 동명이라, nav 파일에서 컴포저블은 alias(`BalanceGameShareScreenRoute`)로 import. 기존에도 동일 동명 구조였음.

- [ ] **Step 2: 공유 State에 인자 보관 + ViewModel에서 파싱**

`BalanceGameShareState.kt` 전체 교체:
```kotlin
package com.whatever.caramel.feature.balancegame.share.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class BalanceGameShareState(
    val isLoading: Boolean = false,
    val gameId: Long = 0L,
    val question: String = "",
    val myNickname: String = "",
    val myGender: String = "",
    val myChoice: String = "",
    val partnerNickname: String = "",
    val partnerGender: String = "",
    val partnerChoice: String = "",
) : UiState
```

`BalanceGameShareViewModel.kt` 의 `createInitialState` 교체(나머지 동일):
```kotlin
import androidx.navigation.toRoute
import com.whatever.caramel.feature.balancegame.share.navigation.BalanceGameShareRoute
```
```kotlin
    override fun createInitialState(savedStateHandle: SavedStateHandle): BalanceGameShareState {
        val args = savedStateHandle.toRoute<BalanceGameShareRoute>()
        return BalanceGameShareState(
            gameId = args.gameId,
            question = args.question,
            myNickname = args.myNickname,
            myGender = args.myGender,
            myChoice = args.myChoice,
            partnerNickname = args.partnerNickname,
            partnerGender = args.partnerGender,
            partnerChoice = args.partnerChoice,
        )
    }
```
> 공유 화면 UI 렌더링은 범위 외 — state 보관까지만.

- [ ] **Step 3: CaramelNavHost 호출부 갱신**

`CaramelNavHost.kt` 에서:
- `balanceGameHistoryScreen(navigateToBack = { popBackStack() })` →
```kotlin
            balanceGameHistoryScreen(
                navigateToBack = { popBackStack() },
                navigateToShare = { gameId, question, myNickname, myGender, myChoice, partnerNickname, partnerGender, partnerChoice ->
                    navigateToBalanceGameShare(
                        gameId = gameId,
                        question = question,
                        myNickname = myNickname,
                        myGender = myGender,
                        myChoice = myChoice,
                        partnerNickname = partnerNickname,
                        partnerGender = partnerGender,
                        partnerChoice = partnerChoice,
                    )
                },
            )
```
- 홈/메인으로 전달하던 `navigateToBalanceGameShare = { navigateToBalanceGameShare() }` (line ~191) →
```kotlin
                navigateToBalanceGameShare = { gameId, question, myNickname, myGender, myChoice, partnerNickname, partnerGender, partnerChoice ->
                    navigateToBalanceGameShare(
                        gameId = gameId,
                        question = question,
                        myNickname = myNickname,
                        myGender = myGender,
                        myChoice = myChoice,
                        partnerNickname = partnerNickname,
                        partnerGender = partnerGender,
                        partnerChoice = partnerChoice,
                    )
                },
```

- [ ] **Step 4: 홈 SideEffect를 데이터화**

`feature/home/.../mvi/HomeSideEffect.kt` 의 `NavigateToBalanceGameShare` 교체:
```kotlin
    data class NavigateToBalanceGameShare(
        val gameId: Long,
        val question: String,
        val myNickname: String,
        val myGender: String,
        val myChoice: String,
        val partnerNickname: String,
        val partnerGender: String,
        val partnerChoice: String,
    ) : HomeSideEffect
```

- [ ] **Step 5: 홈 ViewModel에서 today 게임 데이터로 사이드이펙트 구성**

`HomeViewModel.kt` 의 `ClickShareBalanceGameResult` 분기 교체:
```kotlin
            is HomeIntent.ClickShareBalanceGameResult -> {
                val card = currentState.balanceGameCard
                postSideEffect(
                    HomeSideEffect.NavigateToBalanceGameShare(
                        gameId = card.id,
                        question = card.question,
                        myNickname = currentState.myNickname,
                        myGender = currentState.myGender.name,
                        myChoice = card.myOption?.name ?: "",
                        partnerNickname = currentState.partnerNickname,
                        partnerGender = currentState.partnerGender.name,
                        partnerChoice = card.partnerOption?.name ?: "",
                    ),
                )
            }
```

- [ ] **Step 6: 홈 Route에서 사이드이펙트 → 람다 매핑**

`HomeRoute.kt` 의 `NavigateToBalanceGameShare` 수집부 교체:
```kotlin
                is HomeSideEffect.NavigateToBalanceGameShare ->
                    navigateToBalanceGameShare(
                        sideEffect.gameId,
                        sideEffect.question,
                        sideEffect.myNickname,
                        sideEffect.myGender,
                        sideEffect.myChoice,
                        sideEffect.partnerNickname,
                        sideEffect.partnerGender,
                        sideEffect.partnerChoice,
                    )
```
그리고 `HomeRoute` 파라미터 `navigateToBalanceGameShare: () -> Unit` →
```kotlin
    navigateToBalanceGameShare: (
        gameId: Long,
        question: String,
        myNickname: String,
        myGender: String,
        myChoice: String,
        partnerNickname: String,
        partnerGender: String,
        partnerChoice: String,
    ) -> Unit,
```

- [ ] **Step 7: 홈/메인 Navigation 람다 시그니처 전파**

`feature/home/.../navigation/HomeNavigation.kt`, `feature/main/.../MainRoute.kt`, `feature/main/.../navigation/MainNavigation.kt` 에서 `navigateToBalanceGameShare: () -> Unit` 으로 선언/전달되는 모든 지점을 아래 타입으로 교체:
```kotlin
    navigateToBalanceGameShare: (
        gameId: Long,
        question: String,
        myNickname: String,
        myGender: String,
        myChoice: String,
        partnerNickname: String,
        partnerGender: String,
        partnerChoice: String,
    ) -> Unit,
```
> 각 파일에서 `grep -n "navigateToBalanceGameShare"` 로 선언/전달 위치를 모두 찾아 일관되게 교체. 호출 형태 `navigateToBalanceGameShare = navigateToBalanceGameShare` 는 그대로 통과.

- [ ] **Step 8: 전체 빌드 검증**

Run: `./gradlew :app:assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 9: spotless**

Run: `./gradlew spotlessApply`

- [ ] **Step 10: 커밋**

```bash
git add app feature/balancegame feature/home feature/main
git commit -m "[Feat] 밸런스 게임 공유 라우트 결과 데이터 전달 배선"
```

---

## Task 11: 문자열 리소스 + 최종 검증

**Files:**
- Modify: `feature/balancegame/src/commonMain/composeResources/values/strings.xml`

- [ ] **Step 1: 공유 버튼 문자열 추가**

`strings.xml` 의 History 블록에 추가:
```xml
	<!-- History -->
	<string name="balance_game_history_title">밸런스 게임 히스토리</string>
	<string name="balance_game_history_share_result">결과 공유하기</string>
```
> Task 7에서 참조한 `balance_game_history_share_result` 키. (기존 `balance_game_share_title` 와 별개 — 공유 페이지 타이틀이 아니라 카드 내 버튼 라벨이므로 신규 키)

- [ ] **Step 2: 최종 spotlessCheck**

Run: `./gradlew spotlessCheck`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 최종 빌드**

Run: `./gradlew :app:assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 커밋**

```bash
git add feature/balancegame
git commit -m "[Feat] 밸런스 게임 히스토리 공유 버튼 문자열 추가"
```

---

## Self-Review 결과 (작성자 점검)

- **Spec 커버리지:** 커서 페이징(T1-3, T6), LazyColumn+key/contentType(T8), 아코디언 애니메이션(T6 toggle, T7 AnimatedVisibility/rotate), 서버 순서 유지(T6 정렬 없음), 결과 공유 full-data 전달(T5,6,9,10), 프로필 기본 에셋(T7) — 모두 태스크 존재.
- **추정 API 격리:** 엔드포인트/DTO는 T2에 집중, 변경 시 영향 최소(T2 NOTE).
- **타입 일관성:** `NavigateToShare`/`navigateToShare`/`BalanceGameShareRoute` 8개 필드(gameId, question, myNickname, myGender, myChoice, partnerNickname, partnerGender, partnerChoice) 전 구간 동일. `toHistoryUiModel` 명칭 T4 정의·T6 사용 일치.
- **알려진 리스크:** 디자인시스템 토큰명(`spacing.xs/xxs/xl/xxl`, `typography.body3/body4.bold`, `color.fill.quinary` 등)은 추정 — 각 피처 태스크 빌드 단계에서 실패 시 `core/designsystem`의 실제 심볼로 교정(각 태스크 NOTE에 명시). `feature:main`의 share 람다 전파 지점은 grep로 전수 확인 필요.
