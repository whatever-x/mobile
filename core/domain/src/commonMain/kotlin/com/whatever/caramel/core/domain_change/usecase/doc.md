### 유즈케이스

- 비즈니스 로직별로 분기한다
  - 오늘 스케쥴 불러오기 (GetTodaySchedulesUseCase)
  - id를 통해서 스케쥴 단건 조회하기 (GetScheduleByIdUseCase)
  - 특정 기간동안의 스케쥴들 조회하기 (GetSchedulesByPeriodUseCase)
  - 위의 로직들은 전부 비즈니스 로직을 뜻함

- 단 해당 케이스는 확인을 해야함
  - 정렬의 주체가 UI임, ViewModel에서 진행
  - 정렬의 주체가 도메인임, UseCase에서 진행