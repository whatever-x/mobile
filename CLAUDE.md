# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

**Caramel (카라멜)** — a couple diary app for Android and iOS. Kotlin Multiplatform (KMP) + Compose Multiplatform; nearly all logic and UI live in `commonMain`. Package root: `com.whatever.caramel`. Gradle root project: `caramel`.

## Commands

All Gradle tasks run from repo root via `./gradlew`. `local.properties` (keystore + API keys) is required for the app module to build.

```bash
# Format & lint (Spotless + ktlint). spotlessApply also runs on every commit via pre-commit hook.
./gradlew spotlessApply        # auto-fix formatting
./gradlew spotlessCheck        # verify only — this is what CI gates on

# Tests (Kotlin Multiplatform, written in the commonTest source set).
# Most modules have NO Android target, so there is no `test`/`testDebugUnitTest` task —
# use the platform-specific task. `jvmTest` is the fast feedback loop; `allTests` also runs iOS.
./gradlew :core:util:jvmTest                              # one module, JVM target
./gradlew :core:util:jvmTest --tests "*DateUtilTest*"     # single test class
./gradlew :feature:login:allTests                         # one module, all targets (JVM + iOS)
# Current test homes: core:util, feature:splash, feature:login, feature:profile:create.

# Android build / install. Build types: debug (Caramel-Dev), qa (.qa suffix, debuggable release), release (Caramel).
./gradlew :android-app:assembleDebug
./gradlew :android-app:installDebug
```

iOS: open `app-ios/iosApp.xcodeproj` in Xcode and run. The shared Compose/KMP framework is produced by the `:compose-app` module's iOS targets and consumed by the Xcode project.

## Architecture

Three layers, enforced by module dependencies (see `settings.gradle.kts` and the diagram in `README.md`). Dependencies point **downward only**:

```
:android-app  →  :compose-app, :feature:*  →  :core:domain, :core:ui, :core:designsystem, :core:viewmodel
                         ↑
:core:data (implements domain repositories) → :core:remote, :core:database, :core:datastore
```

- **`:core:domain`** — pure Kotlin. Holds repository **interfaces**, use cases, entities, value objects (`vo`), validators, policies, and the error model. No platform/framework deps.
- **`:core:data`** — implements the domain repository interfaces (`repository/*Impl.kt`), maps DTOs↔domain (`mapper/`), and orchestrates `:core:remote` (Ktor), `:core:database` (Room), `:core:datastore`.
- **`:feature:*`** — one Gradle module per screen/flow (some nested, e.g. `:feature:content:create`). UI + presentation only; depends on domain, never on data directly.
- **`:core:viewmodel`** — `BaseViewModel`, the shared MVI base.

### MVI pattern (every feature follows this)

Each feature module contains, per screen:
- `mvi/<Name>Intent.kt`, `mvi/<Name>State.kt`, `mvi/<Name>SideEffect.kt` — the `UiIntent` / `UiState` / `UiSideEffect` triplet.
- `<Name>ViewModel.kt` extends `BaseViewModel<S, SE, I>` (`core/viewmodel/.../BaseViewModel.kt`). Implement `createInitialState(savedStateHandle)` and `handleIntent(intent)`. Mutate state only through `reduce { copy(...) }`; emit one-shot events through `postSideEffect(...)`; launch coroutines with the provided `launch {}` (it wires the crashlytics-logging `CoroutineExceptionHandler`).
- `<Name>Route.kt` — `@Composable` that collects `state` via `collectAsStateWithLifecycle()`, maps `sideEffect` to navigation/dialog lambdas in a `LaunchedEffect`, and forwards `viewModel.intent(...)` as `onIntent`.
- `<Name>Screen.kt` — stateless UI taking `state` + `onIntent`.
- `navigation/<Name>Navigation.kt` — Compose Navigation entry. `di/Module.kt` — the Koin module for the feature.

A module usually hosts one screen, but a module may host several closely-related screens by giving each its own sub-package that follows the same layout (e.g. `:feature:balancegame` with `history/` and `share/`, each holding `mvi/`, `<Name>ViewModel.kt`, `<Name>Route.kt`, `<Name>Screen.kt`, `navigation/`). The single `di/Module.kt` then registers every ViewModel in the module. Placeholder/shell screens still follow the full MVI layout for consistency.

### Dependency injection (Koin)

Every module exposes a Koin module via a `di/` package. They are all assembled in `compose-app/src/commonMain/.../di/InitKoin.kt` (`initKoin()`), grouped by layer. **A new feature/repository must register its module there** or it won't be injected. ViewModels are obtained in Composables with `koinViewModel()`.

### Errors

Domain failures use `CaramelException` (`core/domain/.../exception/CaramelException.kt`): carries `code`, user-facing `message`/`description`, and an `errorUiType` that the UI maps to dialog vs. toast (see how `Route` composables dispatch `ShowErrorDialog` / `ShowErrorToast` side effects).

## Build conventions

Build setup is centralized in `build-logic/` as convention plugins applied by id in each module's `build.gradle.kts`:
- `caramel.kmp`, `caramel.kmp.ios`, `caramel.compose`, `caramel.kmp.test`, `caramel.android.application`, `caramel.kotlin.serialization`.

When adding a module: create it, `include(":path")` in `settings.gradle.kts`, apply the relevant convention plugins, and add it as a project dependency where consumed (type-safe accessors are enabled — use `projects.feature.home`, not string paths). Versions/libraries come from `gradle/libs.versions.toml` (use `libs.bundles.*` where they exist, e.g. `bundles.ktor`).

## Stack

Compose Multiplatform UI · Koin 4 (DI) · Ktor 3 (HTTP) · Room + DataStore (local) · kotlinx-coroutines/datetime/serialization · Napier (logging) · Firebase (Analytics/Crashlytics/Messaging) · AppsFlyer (deeplink) · Kakao/Apple social auth. Tests: Mokkery (mocking) + Turbine (Flow) + `kotlin.test`, in `commonTest` (provided by the `caramel.kmp.test` plugin pulling in `:core:testing`).

iOS Swift↔Kotlin bridges live under `app-ios/*Bridge/` (Firebase, Kakao login, keychain) and are exposed to common code via expect/actual.
