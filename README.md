<h1 align="left">
  <img src="https://github.com/user-attachments/assets/9be8732c-c998-44fd-8145-d277793725d7" width=5%/> 카라멜(Caramel) - 우리만의 감성 커플 다이어리
</h1>

<p>
  <a href="https://apps.apple.com/kr/app/%EC%B9%B4%EB%9D%BC%EB%A9%9C-caramel-%EC%9A%B0%EB%A6%AC%EB%A7%8C%EC%9D%98-%EA%B0%90%EC%84%B1-%EC%BB%A4%ED%94%8C-%EB%8B%A4%EC%9D%B4%EC%96%B4%EB%A6%AC/id6745321351"><img src="https://github.com/user-attachments/assets/0b054308-2575-4ddb-942f-a89be4e8f464" align="right" width=30%/></a>
  <a href="https://play.google.com/store/apps/details?id=com.whatever.caramel&pcampaignid=web_share"><img src="https://github.com/user-attachments/assets/468fa558-35f7-4c6f-b049-ca6b96374119" align="right" width=32%/></a>
  <br>
  <img src="https://img.shields.io/badge/Kotlin Multiplatform-7F52FF?style=flat&logo=Kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Compose Multiplaform-4285F4?style=flat&logo=jetpackcompose&logoColor=white"/>
  <br>
  <img src="http://img.shields.io/badge/Platform-Android-6EDB8D.svg?style=flat"/>
  <img src="http://img.shields.io/badge/Platform-iOS-EAEAEA.svg?style=flat"/>
  <br>
  <img alt="Android SDK" src="https://img.shields.io/badge/Android Sdk-28%2B-brightgreen.svg?style=flat"/>
  <img alt="iOS" src="https://img.shields.io/badge/iOS-16+-brightgreen.svg?style=flat"/>
</p><br>

<div align="center">
  <img src="https://github.com/user-attachments/assets/375cf029-4e48-4f79-81da-9c44ded41f04" width=180/>
  <img src="https://github.com/user-attachments/assets/88e4f3c7-5d34-49fa-a73f-93cbe95a5a37" width=180/>
  <img src="https://github.com/user-attachments/assets/f085db75-638f-499b-9e7a-97b9c1fd0f0d" width=180/>
  <img src="https://github.com/user-attachments/assets/99d6447e-299b-4af0-915b-691bdb8c281c" width=180/>
  <img src="https://github.com/user-attachments/assets/df9713d2-104f-44af-83d7-0ecb9fc96ed0" width=180/>
</div>

## 📖 Overview
💖 사랑하는 연인과의 모든 순간을 한 곳에!<br>
💕 우리 둘만의 메모부터 일정, 소소한 재미까지 카라멜에서 한 번에 경험할 수 있도록 도와드려요.

📅 한눈에 보는 우리만의 캘린더<br>
&nbsp;&nbsp;&nbsp;&nbsp;→ 기념일과 데이트는 물론, D-DAY까지 함께 기록하고 공유하는 우리만의 특별한 달력을 만들어보세요.<br>
📝 둘만의 기록 저장소<br>
&nbsp;&nbsp;&nbsp;&nbsp;→ 여행지, 영화, 맛집부터 중요한 약속과 투두리스트까지 함께 나누고 언제든 꺼내볼 수 있는 우리만의 기록장을 만들어보세요.<br>
⚖️ 매일 새로운 밸런스 게임<br>
&nbsp;&nbsp;&nbsp;&nbsp;→ 매일 도착하는 새로운 질문에 답하며 예상치 못한 이야기로 서로를 더 깊이 알아가보세요.

## 🗂️ Architecture

```mermaid
graph TD
    %% Core Layer
    Core(":core")
    CoreAnalytics(":core:analytics")
    CoreData(":core:data")
    CoreDataBase(":core:database")
    CoreDataStore(":core:datastore")
    CoreDeeplink(":core:deeplink")
    CoreDesignSystem(":core:designsystem")
    CoreDomain(":core:domain")
    CoreFirebaseMessaging(":core:firebase-messaging")
    CoreRemote(":core:remote")
    CoreTesting(":core:testing")
    CoreUi(":core:designsystem:ui")
    CoreUtil(":core:util")
    CoreViewModel(":core:viewmodel")
    
    %% Feature Layer
    Feature(":feature")
    FeatureCalendar(":feature:calendar")
    FeatureContent(":feature:content")
    FeatureContentCreate(":feature:content:create")
    FeatureContentDetail(":feature:content:detail")
    FeatureContentEdit(":feature:content:edit")
    FeatureCouple(":feature:couple")
    FeatureCoupleConnecting(":feature:couple:connecting")
    FeatureCoupleConnect(":feature:couple:connect")
    FeatureCoupleInvite(":feature:couple:invite")
    FeatureHome(":feature:home")
    FeatureLogin(":feature:login")
    FeatureMain(":feature:main")
    FeatureMemo(":feature:memo")
    FeatureProfile(":feature:profile")
    FeatureProfileEdit(":feature:profile:edit")
    FeatureProfileCreate(":feature:profile:create")
    FeatureSetting(":feature:setting")
    FeatureSplash(":feature:splash")
    
    %% App Layer
    App(:app)
    
    %% Build
    BuildLogic(":build-logic")
    
    %% 1st module
    App --> Core
    App --> Feature
    Core --> CoreDomain
    Core --> CoreAnalytics
    Core --> CoreData
    Core --> CoreDataBase
    Core --> CoreDataStore
    Core --> CoreDeeplink
    Core --> CoreDesignSystem
    Core --> CoreFirebaseMessaging
    Core --> CoreRemote
    Core --> CoreTesting
    Core --> CoreUtil
    Core --> CoreViewModel
    Feature --> CoreDomain
    Feature --> CoreDesignSystem
    Feature --> CoreUi
    Feature --> CoreAnalytics
    Feature --> CoreViewModel
    Feature --> FeatureCalendar
    Feature --> FeatureContent
    %% 2nd module
    FeatureContent --> FeatureContentCreate
    FeatureContent --> FeatureContentDetail
    FeatureContent --> FeatureContentEdit
    Feature --> FeatureCouple
    FeatureCouple --> FeatureCoupleConnecting
    FeatureCouple --> FeatureCoupleConnect
    FeatureCouple --> FeatureCoupleInvite
    Feature  --> FeatureHome
    Feature --> FeatureLogin
    Feature --> FeatureMain
    Feature --> FeatureMemo
    Feature --> FeatureProfile
    FeatureProfile --> FeatureProfileCreate
    FeatureProfile --> FeatureProfileEdit
    Feature --> FeatureSetting
    Feature --> FeatureSplash
    CoreData --> CoreDomain
    CoreTesting--> CoreDomain
    CoreData --> CoreDataStore
    CoreData --> CoreDataBase
    CoreData --> CoreRemote
    CoreData --> CoreUtil
    %% 3rd module
    CoreDesignSystem --> CoreUi
    FeatureSplash --> CoreDeeplink
    FeatureSetting --> CoreUtil
    FeatureProfileEdit --> CoreUtil
    FeatureProfileCreate --> CoreUtil
    FeatureMemo --> CoreUtil
    FeatureMain --> FeatureCalendar
    FeatureMain --> FeatureHome
    FeatureMain --> FeatureMemo
    FeatureMain --> CoreFirebaseMessaging
    FeatureLogin --> CoreFirebaseMessaging
    FeatureHome --> CoreUtil
    FeatureCalendar --> CoreUtil
    FeatureContentCreate --> CoreUtil
    CoreUi --> CoreUtil
```

## 🛠 Libraries

### 🔧 Core
- **Spm4Kmp**: Swift-Kotlin 간 데이터 브릿지
- **Coroutines**: Async task 처리
- **Kotlinx Datetime**: 날짜 및 시간 처리

### 🖼 UI
- **Jetpack Compose**: Base UI Framework
- **Compose Navigation**: 화면 이동 처리

### 📦 Dependency Injection
- **Koin 4.x**: 의존성 주입

### 🌐 Networking & Data
- **Ktor 3.x**: HTTP 클라이언트
- **Preference DataStore**: 로컬 데이터 저장

### 📈 Analytics & Deep Link
- **Firebase**: 분석 및 이벤트 트래킹
- **AppsFlyer**: 딥링크 및 사용자 유입 분석

### 🔐 Authentication & Notification
- **Kakao Auth / Apple Auth**: 소셜 로그인
- **FCM**: 푸시 알림

### 🧪 Testing
- **Mokkery**: 멀티플랫폼 Mocking 프레임워크
- **Turbine**: Flow 테스트 유틸리티

### 🪵 Logging
- **Napier**: KMP 대응 로깅 라이브러리

## 🧑‍💻 Contact & Contributor

<div align="center">
  <table>
    <tr>
      <td align="center">
        <a href="https://github.com/ham2174">
          <img src="https://github.com/Nexters/Funch-AOS/assets/54674781/388e8bd4-3e86-4369-95ce-9956b18c70b5" width="150">
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/RyuSw-cs">
          <img src="https://github.com/user-attachments/assets/765d40e8-ef2b-4674-8314-2a1889b1b337" width="150">
        </a>
      </td>
    </tr>
    <tr>
      <td align="center">
        <p align="center"><a href="https://github.com/ham2174">함건형</a></p>
      </td>
      <td align="center">
        <p align="center"><a href="https://github.com/RyuSw-cs">유승우</a></p>
      </td>
    </tr>
    <tr>
      <td align="center">
        <p><a href="mailto:ham121985@gmail.com">ham121985@gmail.com</a></p>
      </td>
      <td align="center">
        <p><a href="mailto:rsw1452@gmail.com">rsw1452@gmail.com</a></p>
      </td>
    </tr>
  </table>
</div>