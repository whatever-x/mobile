# 밸런스 게임 히스토리 페이지 — 설계 문서

작성일: 2026-06-07
관련 Figma:
- 목록(접힘): node-id `4507-445`
- 카드 펼침: node-id `4507-492`
- fileKey: `CwP0kH9V4EuneQpRUTqofP`

## 목표

밸런스 게임 히스토리 화면을 구현한다. 서버에서 커서 기반 페이징으로 과거 밸런스 게임 결과 목록을 받아 `LazyColumn`으로 표시하고, 카드를 탭하면 애니메이션으로 결과가 펼쳐지며(아코디언, 한 번에 하나), 펼친 카드의 "결과 공유하기"를 누르면 결과 전체 데이터를 들고 공유 페이지로 이동한다.

## 범위

**포함**
- 도메인/데이터/리모트 레이어의 히스토리 페이징 조회 경로
- `:feature:balancegame`의 `history` 화면(상태/인텐트/사이드이펙트/뷰모델/스크린/UI 모델/컴포넌트)
- 공유 페이지로의 네비게이션 하드오프(결과 전체 데이터 전달) — 라우트 시그니처 변경 및 기존 호출부(홈) 수정 포함

**미포함**
- 공유 페이지(`share`)의 UI 구현 (인자만 받도록 배선까지)
- 백엔드 히스토리 API 확정 (현재 엔드포인트/응답은 추정값)

## 현재 상태

- `:feature:balancegame` 모듈에 `history/`, `share/` MVI 스캐폴딩이 존재하나 본문은 `CaramelTopBar`만 있는 빈 화면.
- 네비게이션 배선(홈 → 히스토리/공유)은 이미 완료됨 (`app/CaramelNavHost.kt:211`, 홈 `HomeSideEffect.NavigateToBalanceGameHistory/Share`).
- 도메인에 `BalanceGameResult(gameInfo, myChoice, partnerChoice)` 존재. 단, 히스토리 목록/페이징 경로는 도메인·데이터·리모트 어디에도 없음 (`/today`, `/{gameId}`만 존재).
- 커서 페이징 레퍼런스: 메모(`MemoWithCursor`, `CursoredContentResponse { list, cursor { next } }`, `GET /v1/content/memo?size=&cursor=`).
- 프로필 렌더링 레퍼런스: 홈 `feature/home/.../components/quiz/BalanceGameResult.kt` — `Gender` → `Resources.Image.img_quiz_man/woman`, 닉네임/성별은 `GetCoupleRelationshipInfoUseCase`로 커플 단위 조회.

## 디자인 분석

**접힌 카드 (History item header / collapsed)**
- 배경 `color.background.tertiary`(White), 테두리 `color.fill.quaternary`, radius 16, padding `px16 py14`.
- 좌: 질문(`typography.heading3`, 1줄 말줄임) + 날짜(`typography.body3.regular`, `color.text.secondary`, 형식 `2025.03.19`).
- 우: chevron 아이콘. 접힘 = 아래 방향(`ic_arrow_down_16`), 펼침 = 위 방향(`ic_arrow_up_16`). 회전 애니메이션 적용.

**펼친 카드 (History item / expanded)**
- 헤더(위와 동일) + 결과 카드:
  - 결과 카드: 배경 `background.tertiary`, 테두리 `fill.quaternary`(굵게), radius 16.
  - 질문(`typography.heading2`, 20sp, 가운데, 최대 2줄).
  - divider (`color.divider.primary`).
  - 프로필 2행: 아바타(50dp, `Gender`별 기본 에셋) + 닉네임(`body4.regular`, `text.secondary`) + 선택 텍스트(`body1.bold`, `text.primary`).
    - 1행: 내 정보 / 2행: 상대 정보.
  - "결과 공유하기" 버튼: 배경 `color.fill.quinary`, `ic_share_16` + 텍스트(`body4.bold`), radius 16.

## 아키텍처 (레이어별 변경)

의존성은 아래로만 향한다. `:feature` → `:core:domain`. 데이터 접근은 use case 경유.

### `:core:domain`
- 신규 `vo/balanceGame/BalanceGameHistory.kt`
  ```kotlin
  data class BalanceGameHistory(
      val nextCursor: String?,
      val results: List<BalanceGameResult>,
  )
  ```
- `repository/BalanceGameRepository.kt`에 추가:
  ```kotlin
  suspend fun getBalanceGameHistory(size: Int?, cursor: String?): BalanceGameHistory
  ```
- 신규 `usecase/balanceGame/GetBalanceGameHistoryUseCase.kt` (`GetMemoListUseCase` 미러, 기본값 인자).
- `di/Module.kt`에 `factory { GetBalanceGameHistoryUseCase(get()) }` 등록.

### `:core:remote`
- 신규 `dto/balanceGame/response/BalanceGameHistoryResponse.kt`
  ```kotlin
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
  - 항목 단위는 기존 `BalanceGameResponse(gameInfo, myChoice, partnerChoice)` 재사용.
- `RemoteBalanceGameDataSource(Impl)`에 추가:
  ```kotlin
  suspend fun fetchBalanceGameHistory(size: Int?, cursor: String?): BalanceGameHistoryResponse
  // GET "$BALANCE_GAME_BASE_URL?size=&cursor=" (parameter 로 nullable 처리, 메모 방식)
  ```
  - ⚠️ 엔드포인트/응답 형식은 추정. 백엔드 확정 시 DTO·매퍼·엔드포인트만 수정하면 되도록 격리.

### `:core:data`
- `mapper/BalanceGameMapper.kt`에 `BalanceGameHistoryResponse.toBalanceGameHistory()` 추가 (기존 `toBalanceGameResult()` 재사용).
- `repository/BalanceGameRepositoryImpl.kt`에 `getBalanceGameHistory` 구현 (`safeCall`).

## Feature (`:feature:balancegame` / `history`)

### MVI
- `mvi/BalanceGameHistoryState.kt`
  ```kotlin
  data class BalanceGameHistoryState(
      val isLoading: Boolean = false,        // 첫 페이지 로딩
      val isLoadingMore: Boolean = false,    // 추가 페이지 로딩
      val isEndReached: Boolean = false,
      val nextCursor: String? = null,
      val myNickname: String = "",
      val myGender: Gender = Gender.IDLE,
      val partnerNickname: String = "",
      val partnerGender: Gender = Gender.IDLE,
      val items: ImmutableList<BalanceGameHistoryUiModel> = persistentListOf(),
      val expandedGameId: Long? = null,      // 아코디언: 하나만
  ) : UiState
  ```
- `mvi/BalanceGameHistoryIntent.kt`
  - `ClickBackButton`
  - `ClickHistoryCard(gameId: Long)` — 아코디언 토글
  - `LoadMore` — 마지막 항목 도달 시
  - `ClickShareResult(gameId: Long)`
- `mvi/BalanceGameHistorySideEffect.kt`
  - `NavigateToBack`
  - `NavigateToShare(args: BalanceGameShareArgs)` — 결과 전체 데이터

### UI 모델
- `model/BalanceGameHistoryUiModel.kt`
  ```kotlin
  data class BalanceGameHistoryUiModel(
      val gameId: Long,
      val question: String,
      val dateText: String,        // "2025.03.19"
      val myChoiceText: String,
      val partnerChoiceText: String,
  )
  ```
  - 매퍼: `BalanceGameResult.toHistoryUiModel()`. 날짜는 `LocalDate` → `yyyy.MM.dd` 포맷.

### ViewModel
- 생성자에 `GetBalanceGameHistoryUseCase`, `GetCoupleRelationshipInfoUseCase` 주입.
- 진입(`createInitialState` 후 init 또는 첫 intent)에서 커플 정보 + 첫 페이지 **병렬 로드**(`launch` + `joinAll`, 홈 패턴 참고).
- `ClickHistoryCard`: `expandedGameId = if (current == id) null else id`.
- `LoadMore`: `isLoadingMore`/`isEndReached`/`nextCursor` 가드 후 다음 페이지 append.
- `ClickShareResult`: 해당 항목 + 커플 정보로 `BalanceGameShareArgs` 구성 후 `NavigateToShare` 사이드이펙트.
- 순서: 서버 응답 순서 유지(클라 정렬 없음).
- 에러: `CaramelException` → `errorUiType` 기준으로 다이얼로그/토스트 사이드이펙트(기존 Route 패턴).

### Screen
- `LazyColumn`, `items(items, key = { it.gameId }, contentType = { "history_item" })`.
- 마지막 인덱스 근접 시 `onIntent(LoadMore)` (또는 `LaunchedEffect` + `derivedStateOf`).
- 카드 컴포넌트 `components/BalanceGameHistoryCard.kt`:
  - 접힌 헤더(질문/날짜/chevron) 항상 표시.
  - 결과 영역 `AnimatedVisibility`(expand/shrink vertically) — `expanded = (expandedGameId == gameId)`.
  - chevron 회전 `animateFloatAsState` (0f ↔ 180f) 또는 아이콘 스왑.
  - 프로필 렌더링은 홈 `BalanceGameResult` 방식 재사용(필요 시 내부에 동등 컴포저블 작성, 모듈 경계상 복제).
- `isLoadingMore`일 때 하단 로딩 인디케이터 item.

### Route
- 사이드이펙트 수집해 `navigateToBack`, `navigateToShare(args)` 람다로 매핑.

## 공유 페이지 네비게이션 (결과 전체 전달)

- `share/navigation/BalanceGameShareNavigation.kt`
  - `BalanceGameShareRoute`를 `data object` → `@Serializable data class`로 변경:
    ```kotlin
    @Serializable
    data class BalanceGameShareRoute(
        val gameId: Long,
        val question: String,
        val date: String,             // ISO yyyy-MM-dd
        val myNickname: String,
        val myGender: String,         // Gender.name
        val myChoice: String,
        val partnerNickname: String,
        val partnerGender: String,
        val partnerChoice: String,
    )
    ```
  - `navigateToBalanceGameShare(args...)` 시그니처 변경.
- **파급 수정**:
  - `app/CaramelNavHost.kt` — `balanceGameShareScreen` 및 두 호출부(`navigateToBalanceGameHistory`/`Share`) 갱신.
  - 홈(`feature/home`)의 "결과 공유하기" 경로(`HomeSideEffect.NavigateToBalanceGameShare`) — 오늘 게임 결과 데이터를 args로 전달하도록 수정. 홈은 이미 `HomeState`에 닉네임/성별/선택 보유.
- 공유 화면(`BalanceGameShareScreen`/`ViewModel`)은 인자를 받아 state에 보관하는 수준까지만(렌더링은 범위 외). `SavedStateHandle`에서 라우트 인자 파싱.

## 데이터 흐름 요약

```
[진입]
HistoryRoute → ViewModel.init
  ├─ GetCoupleRelationshipInfoUseCase → myNickname/myGender/partnerNickname/partnerGender
  └─ GetBalanceGameHistoryUseCase(size, cursor=null)
        → Repository.getBalanceGameHistory
        → RemoteDataSource.fetchBalanceGameHistory (GET /v1/balance-game?size=&cursor=)
        → BalanceGameHistoryResponse → toBalanceGameHistory() → BalanceGameHistory
        → items += results.map { toHistoryUiModel() }, nextCursor 갱신

[스크롤 끝]
Intent.LoadMore → 가드 후 다음 페이지 append

[카드 탭]
Intent.ClickHistoryCard(id) → expandedGameId 토글(아코디언)

[공유]
Intent.ClickShareResult(id) → NavigateToShare(BalanceGameShareArgs) → 공유 라우트
```

## 검증 기준

- `./gradlew :core:domain:jvmTest`, 관련 모듈 컴파일 통과.
- `./gradlew spotlessCheck` 통과.
- `./gradlew :feature:balancegame:compileDebugKotlinAndroid`(또는 전체 `assembleDebug`) 빌드 성공.
- 동작: 목록 표시 / 스크롤 페이징 / 아코디언 펼침·접힘 애니메이션 / "결과 공유하기" 이동(데이터 전달) / 뒤로가기.

## 미해결·추정

- 히스토리 API 엔드포인트(`GET /v1/balance-game`)와 응답(`{ list, cursor: { next } }`)은 추정값. 백엔드 확정 시 DTO·매퍼·엔드포인트만 교체.
- 페이지 `size` 기본값은 메모 관례에 맞춰 호출 시 지정(미지정 시 서버 기본).
