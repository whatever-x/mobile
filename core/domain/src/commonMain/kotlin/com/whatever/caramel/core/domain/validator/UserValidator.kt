package com.whatever.caramel.core.domain.validator

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.UserErrorCode

/**
 * User Entity에 대한 유효성 검증
 * @author RyuSw-cs
 * @since 2025.03.30
 * */
object UserValidator {
    /**
     * 닉네임 입력창에 대한 유효성 검사 진행
     * @param input 닉네임 입력값
     * @return 검증에 대한 Result 객체
     * */
    fun checkInputNicknameValidate(input: String): Result<Unit> =
        when {
            input.length > NICKNAME_MAX_LENGTH ->
                Result.failure(
                    CaramelException(
                        code = UserErrorCode.INVALID_NICKNAME_LENGTH,
                        message = "닉네임은 $NICKNAME_MAX_LENGTH 자리 이하여야 합니다.",
                        debugMessage = "Nickname is limited to $NICKNAME_MAX_LENGTH characters.",
                        errorUiType = ErrorUiType.TOAST,
                    ),
                )
            !input.matches(NICKNAME_VALID_PATTERN) ->
                Result.failure(
                    CaramelException(
                        code = UserErrorCode.INVALID_NICKNAME_CHARACTER,
                        message = "닉네임은 영문, 숫자, 한글만 사용할 수 있습니다.",
                        debugMessage = "Nickname should only contain English letters, numbers, and Korean characters.",
                        errorUiType = ErrorUiType.TOAST,
                    ),
                )
            else -> Result.success(Unit)
        }

    /**
     * 닉네임에 대한 값을 서버 전달시에 필요한 유효성 검사 진행
     * @param input 닉네임 입력값
     * @return 검증에 대한 Result 객체
     * */
    fun checkNicknameValidate(input: String): Result<String> =
        when {
            input.length > NICKNAME_MAX_LENGTH ->
                Result.failure(
                    CaramelException(
                        code = UserErrorCode.INVALID_NICKNAME_LENGTH,
                        message = "닉네임은 $NICKNAME_MIN_LENGTH 자리 이상, $NICKNAME_MAX_LENGTH 자리 이하여야 합니다.",
                        debugMessage =
                            "Nicknames must have at least $NICKNAME_MIN_LENGTH " +
                                "character and no more than $NICKNAME_MAX_LENGTH characters.",
                        errorUiType = ErrorUiType.TOAST,
                    ),
                )
            !input.matches(NICKNAME_VALID_PATTERN) ->
                Result.failure(
                    CaramelException(
                        code = UserErrorCode.INVALID_NICKNAME_CHARACTER,
                        message = "닉네임은 영문, 숫자, 한글만 사용할 수 있습니다.",
                        debugMessage = "Nickname should only contain English letters, numbers, and Korean characters.",
                        errorUiType = ErrorUiType.TOAST,
                    ),
                )
            else -> Result.success(input)
        }

    const val NICKNAME_MIN_LENGTH = 2
    const val NICKNAME_MAX_LENGTH = 8
    private val NICKNAME_VALID_PATTERN = Regex("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]*\$")
}
