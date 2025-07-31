package com.whatever.caramel.core.domain.validator

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.ContentErrorCode
import com.whatever.caramel.core.domain.validator.util.codePointCount

object ContentValidator {
    fun checkInputTitleValidate(input: String): Result<String> =
        when {
            input.codePointCount() > MAX_TITLE_LENGTH ->
                Result.failure(
                    exception =
                        CaramelException(
                            code = ContentErrorCode.MAX_LENGTH_TITLE,
                            message = "제목은 ${MAX_TITLE_LENGTH}자까지 입력할 수 있어요",
                            debugMessage = "제목 최대 길이 초과",
                            errorUiType = ErrorUiType.TOAST,
                        ),
                )
            input.contains("\n") -> {
                Result.failure(
                    exception =
                        CaramelException(
                            code = ContentErrorCode.CAN_NOT_CHANGE_OF_LINE,
                            message = "줄바꿈을 포함할수 없어요",
                            debugMessage = "타이틀에 줄바꿈 포함 시도",
                            errorUiType = ErrorUiType.TOAST,
                        ),
                )
            }
            else -> Result.success(value = input)
        }

    fun checkInputBodyValidate(input: String): Result<String> =
        when {
            input.codePointCount() > MAX_BODY_LENGTH ->
                Result.failure(
                    exception =
                        CaramelException(
                            code = ContentErrorCode.MAX_LENGTH_BODY,
                            message = "본문은 ${MAX_BODY_LENGTH}자까지 입력할 수 있어요",
                            debugMessage = "본문 최대 길이 초과",
                            errorUiType = ErrorUiType.TOAST,
                        ),
                )
            else -> Result.success(input)
        }

    const val MAX_TITLE_LENGTH = 30
    const val MAX_BODY_LENGTH = 5000
}
