# 🚀 Android/iOS Fastlane 배포 가이드

Caramel 프로젝트는 **Fastlane**을 사용해 Android 앱을 Firebase App Distribution으로 iOS 앱을 Test Flight로 테스트 배포합니다.  
아래 단계를 따라 로컬에서 테스트 배포를 진행 할 수 있습니다.

---

## ✅ 1. 필수 설치

### 1-1. Ruby 환경 준비
- macOS 기준 권장: **rbenv**로 관리
  ```bash
  brew install rbenv ruby-build
  rbenv install 3.2.3 
  rbenv global 3.2.3
  ruby -v
  ```

### 1-2 Bundler 설치
- macOs 기준
  ```bash
  gem install bundler
  ```
  
## ✅ 2. 의존성 설치
- 프로젝트 루트로 이동 후
  ```bash
  bundle install
  ```
  
## ✅ 3. FirebaseCLI 설치 및 .env 파일 구성
- 로컬 터미널에서 실행
  ```bash
  # node가 없을 경우에만 실행
  brew install node
  
  npm install -g firebase-tools
  firebase login:ci
  ```

- 로그인 이후 획득한 FirebaseCLI 토큰은 **.env** 파일에 적용
  - ex) `FIREBASE_CLI_TOKEN=<획득한 토큰>`

## 🚀 4. 배포 진행
- 환경 설정이 완료되면 다음을 꼭 체크
  - ✅ 버전코드 확인
  - ✅ 릴리즈 노트 확인
- 체크가 완료되면 프로젝트 루트에서 다음을 실행
  ```bash
  # Android 배포 명령어
  bundle exec fastlane android beta
  
  # iOS 배포 명령어
  ```