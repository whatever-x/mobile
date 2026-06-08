# 발란스 게임 — 히스토리 / 공유하기 진입점 추가 설계

작성일: 2026-06-07
브랜치: `feat/balance_game_improve`
Figma: `CwP0kH9V4EuneQpRUTqofP` 노드 `4467-7446` (페이지 "🍀 (작업중) 밸런스 게임 히스토리")

## 목표

홈 화면 발란스 게임 카드에 **히스토리**·**공유하기** 진입 버튼을 추가하고, 각각 **셸(껍데기) 화면**으로 이동시킨다. 셸 화면의 실제 내용은 이후 별도 작업으로 채운다. 본 작업 완료 후 브랜치에 푸시한다.

## 범위 (Scope)

포함:
- 발란스 게임 카드에 히스토리 진입 링크 추가 (항상 노출)
- 발란스 게임 카드에 공유하기 버튼 추가 (결과 노출 시점에만)
- 히스토리 셸 화면 + 공유하기 셸 화면 (TopAppBar + 빈 본문)
- 두 화면으로의 네비게이션 배선
- 공유 아이콘(`ic_share_16`) 디자인 시스템 추가

제외 (YAGNI):
- 히스토리 목록/펼침 실제 UI 및 데이터 연동
- 공유하기 실제 동작(이미지 생성, OS 공유 시트 등)

> 구현 결정 변경: 셸 화면도 프로젝트의 "모든 feature는 MVI" 컨벤션을 정확히 따르기 위해 전체 MVI 스캐폴딩(Intent/State/SideEffect/ViewModel/Route/Screen/navigation)을 갖춘다. 현재는 뒤로가기 인텐트만 처리한다.

## 디자인 매핑 (Figma 확인 완료)

| 요소 | Figma 노드 | 노출 조건 | 위치 | 스타일 |
|---|---|---|---|---|
| 히스토리 진입 | `Link / balance game history` (4467:7499) | **항상** | 카드 헤더 우측 상단 ("오늘의 카라멜" 행) | "히스토리" 13sp `text.secondary` + `ic_arrow_right_16` |
| 공유하기 | `btn_share_result` (4467:7483) | **결과 노출 시** | 결과(뒷면) 카드 하단, 프로필 결과 아래 | `fill.quinary` 배경, `ic_share`(16) + "결과 공유하기" 13sp `text.primary`, radius `l`(16) |
| 히스토리 화면 | `히스토리 - 목록` (4507:445) | — | 신규 풀스크린 | TopAppBar(back + "밸런스 게임 히스토리") + 빈 본문 (셸) |
| 공유 화면 | 전용 디자인 없음 | — | 신규 풀스크린 | TopAppBar(back + 임시 타이틀) + 빈 본문 (셸) |

### "결과 노출 시점"의 정의

현재 `BalanceGameCard.GameResult`:
- `IDLE`: 내 선택 전 / `WAITING`: 내 선택 완료, 상대 대기 / `CHECK_RESULT`: 둘 다 선택 완료

카드는 `CHECK_RESULT`에서 "결과 확인하기" 탭 → Y축 회전(`isBalanceGameCardRotated = true`) → 뒷면에 `BalanceGameResult`(양쪽 프로필 + 선택) 노출. Figma의 공유 버튼이 있는 카드(4467:7446)가 이 **뒷면(결과 노출 상태)** 와 동일하다.

→ **공유 버튼 노출 조건 = `isBalanceGameCardRotated == true` 분기 (뒷면)**. (= 둘 다 선택 후 결과가 나온 타이밍)

### "항상 노출"의 구현 근거

`Quiz.kt`에서 헤더("오늘의 카라멜")와 `QuestionArea`는 게임 상태와 무관하게 항상 렌더링되고, 카드 전체가 회전하되 콘텐츠는 `rotationY=180`으로 역보정되어 앞/뒷면 모두 정상 표시된다. → 히스토리 링크를 헤더 영역에 두면 모든 상태에서 노출된다.

## 아키텍처

### 모듈 구조 (결정됨)

신규 단일 모듈 **`:feature:balancegame`** 에 두 셸 화면을 모두 둔다.
- 발란스 게임이라는 하나의 서브피처로 묶어 보일러플레이트 최소화. 추후 화면이 커지면 `:feature:balancegame:history` 등으로 분리.
- `settings.gradle.kts`에 `include(":feature:balancegame")` 추가.
- 컨벤션 플러그인: `caramel.kmp`, `caramel.kmp.android`, `caramel.kmp.ios`, `caramel.compose`, `caramel.kotlin.serialization`, `caramel.kmp.test`.
- 의존성: `projects.core.designsystem`, `projects.core.ui`, `projects.core.domain`, `libs.koin.*`, `libs.jetbrains.androidx.compose.navigation`.

### 모듈 파일 구성

```
feature/balancegame/
  build.gradle.kts
  src/commonMain/kotlin/com/whatever/caramel/feature/balancegame/
    history/
      BalanceGameHistoryRoute.kt    // stateless Composable
      BalanceGameHistoryScreen.kt   // TopAppBar + 빈 본문
      navigation/BalanceGameHistoryNavigation.kt  // @Serializable Route + composable<>
    share/
      BalanceGameShareRoute.kt
      BalanceGameShareScreen.kt
      navigation/BalanceGameShareNavigation.kt
```

두 화면 모두 MVI를 따르며, `di/Module.kt`(`balanceGameFeatureModule`)에 두 ViewModel을 등록하고 `InitKoin.kt`에 모듈을 추가한다.

### 네비게이션 배선 (setting 패턴 동일)

히스토리/공유 화면은 하단 탭 위를 덮는 풀스크린이므로 **앱 레벨 `CaramelNavHost`** 에 등록한다. 콜백은 home → main → app NavHost로 버블링한다 (setting과 동일 경로).

호출 체인:
```
Quiz.kt onClickHistory / onClickShare
  → HomeScreen onIntent
  → HomeIntent.ClickBalanceGameHistory / ClickShareResult
  → HomeViewModel.handleIntent → postSideEffect
  → HomeSideEffect.NavigateToBalanceGameHistory / NavigateToBalanceGameShare
  → HomeRoute (LaunchedEffect) → navigateToBalanceGameHistory()/navigateToBalanceGameShare()
  → HomeNavigation.homeContent(파라미터 추가)
  → MainRoute (homeContent 호출부에 람다 전달)
  → MainNavigation.mainGraph(파라미터 추가)
  → CaramelNavHost: mainGraph 호출부에서 navigateToBalanceGameHistory()/Share() 연결
      + balanceGameHistoryScreen { popBackStack() } / balanceGameShareScreen { popBackStack() } 등록
```

각 셸 화면은 뒤로가기(`popBackStack()`)만 받는다.

### 디자인 시스템 변경

- `ic_share_16.xml` 추가: Figma `ic_share / 16` (노드 4489:448) 를 SVG export → Android vector drawable로 변환하여 `core/designsystem/.../composeResources/drawable/` 에 배치.
- `Resources.kt`의 `Icon`에 `val ic_share_16 = Res.drawable.ic_share_16` 등록 (+ import).
- 히스토리 chevron은 기존 `ic_arrow_right_16` 재사용.

### 문자열 리소스

`feature/home` 리소스에 추가:
- `balance_game_history` = "히스토리"
- `share_result` = "결과 공유하기"

`feature/balancegame` 리소스:
- `balance_game_history_title` = "밸런스 게임 히스토리"
- 공유 화면 임시 타이틀 (예: "결과 공유하기")

## 변경 파일 요약

신규:
- `settings.gradle.kts` (include 1줄)
- `feature/balancegame/build.gradle.kts`
- `feature/balancegame/src/commonMain/.../history/{Route,Screen,navigation}` 3파일
- `feature/balancegame/src/commonMain/.../share/{Route,Screen,navigation}` 3파일
- `core/designsystem/.../drawable/ic_share_16.xml`
- 모듈 리소스 strings.xml (commonMain composeResources)

수정:
- `feature/home/.../components/quiz/Quiz.kt` (헤더 히스토리 링크 + 뒷면 공유 버튼, 콜백 2개 추가)
- `feature/home/.../HomeScreen.kt` (quiz 호출부 콜백 연결)
- `feature/home/.../mvi/HomeIntent.kt` (intent 2개)
- `feature/home/.../mvi/HomeSideEffect.kt` (side effect 2개)
- `feature/home/.../HomeViewModel.kt` (handleIntent 분기 2개)
- `feature/home/.../HomeRoute.kt` (nav 람다 2개 + side effect 처리)
- `feature/home/.../navigation/HomeNavigation.kt` (homeContent 파라미터 2개)
- `feature/home/build.gradle.kts` (필요 시 — 단순 nav 람다라 미변경 가능)
- `feature/main/.../MainRoute.kt` (homeContent 호출부 람다 전달)
- `feature/main/.../navigation/MainNavigation.kt` (mainGraph 파라미터 2개)
- `feature/main/build.gradle.kts` (`projects.feature.balancegame` 의존성 추가)
- `app/.../CaramelNavHost.kt` (screen 등록 + navigate 연결)
- `app/build.gradle.kts` (`projects.feature.balancegame` 의존성 추가)
- `core/designsystem/.../foundations/Resources.kt` (ic_share_16 등록)

## 검증 기준

1. `./gradlew :feature:balancegame:compileKotlinJvm` (또는 모듈 빌드) 성공
2. `./gradlew :app:assembleDebug` 성공
3. `./gradlew spotlessCheck` 통과
4. 수동 확인: 홈 카드에 "히스토리 >" 항상 노출 → 탭 시 히스토리 셸 진입 → 뒤로가기 동작
5. 수동 확인: 발란스 게임 둘 다 선택 후 결과 뒤집힘 상태에서 "결과 공유하기" 노출 → 탭 시 공유 셸 진입 → 뒤로가기 동작
6. 결과 노출 전(IDLE/WAITING/앞면)에는 공유 버튼 미노출

## 미해결/가정

- 공유 화면은 전용 Figma 디자인이 없어 TopAppBar + 빈 본문 셸로만 둔다 (사용자 승인됨).
- `ic_share` 아이콘은 디자인 시스템에 없어 신규 추가가 필요하다.
- 히스토리 진입은 Figma 조립 화면(4467:7398)이 사용하는 우측 상단 링크 방식을 채택한다 (대안인 풀폭 "밸런스 게임 내역 보러가기" 버튼 4467:7486은 미채택).
