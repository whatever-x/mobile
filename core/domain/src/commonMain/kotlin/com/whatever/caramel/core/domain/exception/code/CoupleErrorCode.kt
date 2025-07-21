package com.whatever.caramel.core.domain.exception.code

data object CoupleErrorCode {
    private const val PREFIX = "COUPLE"

    const val INVALID_USER_STATUS = PREFIX + "001" // SINGLE 유저가 아님
    const val INVITATION_CODE_GENERATION_FAIL = PREFIX + "002"
    const val INVITATION_CODE_EXPIRED = PREFIX + "003" // 초대코드 만료, 혹은 유효하지 않은 코드
    const val INVITATION_CODE_SELF_GENERATED = PREFIX + "004" // 자기 자신이 발급한 코드를 사용
    const val MEMBER_NOT_FOUND = PREFIX + "005" // 자신 혹은 상대방 정보를 찾을 수 없음
    const val COUPLE_NOT_FOUND = PREFIX + "006"
    const val NOT_A_MEMBER = PREFIX + "007"
    const val ILLEGAL_MEMBER_SIZE = PREFIX + "008"
    const val UPDATE_FAIL = PREFIX + "009"
    const val CAN_NOT_LOAD_DATA = PREFIX + "011" // 커플 연결 해제 되었을 때 데이터를 불러오지 못함
}
