package com.whatever.caramel.core.testing.constant

data object TestMessage {
    const val NOT_USE_IN_TEST = "테스트에서 사용하지 않는 메소드입니다."
    const val REQUIRE_INIT_FOR_TEST = "해당 테스트를 위해서는 변수 초기화가 필요합니다."

    const val TOKEN_REFRESH_FAIL = "토큰 갱신에 실패했습니다."

    const val INVALID_ARGUMENT = "잘못된 인자가 제공되었습니다."
    const val ARGS_VALIDATION_ERROR = "인자 유효성 검증에 실패했습니다."
    const val UNKNOWN_ERROR = "알 수 없는 오류가 발생했습니다."
}