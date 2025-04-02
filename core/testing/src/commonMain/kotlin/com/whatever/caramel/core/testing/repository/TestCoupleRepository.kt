package com.whatever.caramel.core.testing.repository

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.testing.constant.TestMessage

class TestCoupleRepository : CoupleRepository {
    var coupleInvitationCodeResponse: CoupleInvitationCode? = null
    var connectCoupleResponse: Couple? = null

    override suspend fun getCoupleInvitationCode(): CoupleInvitationCode {
        return coupleInvitationCodeResponse ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.REQUIRE_INIT_FOR_TEST
        )
    }

    override suspend fun connectCouple(invitationCode: String): Couple {
        return connectCoupleResponse ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.REQUIRE_INIT_FOR_TEST
        )
    }
}