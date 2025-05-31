package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.memo.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.memo.response.GetMemoResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteMemoDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteMemoDataSource {
    override suspend fun createMemo(request: CreateMemoRequest): CreateMemoResponse =
        authClient.post(MEMO_BASE_URL) {
            setBody(body = request)
        }.getBody()

    override suspend fun updateMemo(memoId: Long, updateMemoRequest: UpdateMemoRequest) {
        authClient.put("$MEMO_BASE_URL/$memoId") {
            setBody(updateMemoRequest)
        }
    }

    override suspend fun deleteMemo(memoId: Long) {
        authClient.delete("$MEMO_BASE_URL/$memoId")
    }

    override suspend fun getMemoDetail(memoId: Long): GetMemoResponse {
        return authClient.get("$MEMO_BASE_URL/$memoId").getBody()
    }

    companion object {
        private const val MEMO_BASE_URL = "v1/content/memo"
    }
} 