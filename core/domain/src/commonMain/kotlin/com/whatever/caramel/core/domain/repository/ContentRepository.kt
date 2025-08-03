package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.params.content.memo.MemoEditParameter
import com.whatever.caramel.core.domain.params.content.memo.MemoParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter
import com.whatever.caramel.core.domain.vo.content.LinkMetaData
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor

interface ContentRepository {

    suspend fun getLinkMetadata(url: String): LinkMetaData?

    suspend fun getTags(): List<Tag>

    // Memo
    suspend fun createMemo(parameter: MemoParameter)

    suspend fun getMemoList(
        size: Int?,
        cursor: String?,
        sortType: String?,
        tagId: Long?,
    ): MemoWithCursor

    suspend fun updateMemo(
        memoId: Long,
        parameter: MemoEditParameter,
    )

    suspend fun deleteMemo(memoId: Long)

    suspend fun getMemo(memoId: Long): Memo

    // Schedule
    suspend fun createSchedule(parameter: ScheduleParameter)

    suspend fun getSchedule(scheduleId: Long): Schedule

    suspend fun updateSchedule(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    )

    suspend fun getScheduleList(
        startDate: String,
        endDate: String,
        userTimezone: String?,
    ): List<Schedule>

    suspend fun deleteSchedule(scheduleId: Long)

}