package com.whatever.caramel.feature.memo.model

import androidx.compose.runtime.Immutable
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.util.DateFormatter.formatWithSeparator

@Immutable
data class MemoUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val tagListText: String,
    val createdAt: String,
    val contentAssignee: ContentAssignee,
)

fun Memo.toUiModel() =
    MemoUiModel(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        tagListText = this.tagList.joinToString(separator = ",") { it.label },
        createdAt = this.createdAt.formatWithSeparator(separator = "."),
        contentAssignee = this.contentAssignee,
    )

@Immutable
data class TagUiModel(
    val id: Long? = null,
    val label: String = "",
) {
    companion object {
        fun toUiModel(tag: Tag) =
            TagUiModel(
                id = tag.id,
                label = tag.label,
            )
    }
}
