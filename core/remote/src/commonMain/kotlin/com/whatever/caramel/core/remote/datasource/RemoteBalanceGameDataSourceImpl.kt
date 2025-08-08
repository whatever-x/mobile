package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.balanceGame.request.ChooseBalanceGameRequest
import com.whatever.caramel.core.remote.dto.balanceGame.response.BalanceGameResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteBalanceGameDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteBalanceGameDataSource {
    override suspend fun fetchBalanceGameOfToday(): BalanceGameResponse = authClient.get("$BALANCE_GAME_BASE_URL/today").getBody()

    override suspend fun sendChooseOption(
        gameId: Long,
        request: ChooseBalanceGameRequest,
    ): BalanceGameResponse =
        authClient
            .post("$BALANCE_GAME_BASE_URL/$gameId") {
                setBody(body = request)
            }.getBody()

    companion object {
        private const val BALANCE_GAME_BASE_URL = "/v1/balance-game"
    }
}
