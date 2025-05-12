package com.whatever.caramel.core.domain.usecase.tag

import com.whatever.caramel.core.domain.entity.Tag

class GetTagUseCase() {

    suspend operator fun invoke(): List<Tag> {
        return buildList {
            add(Tag(id = 1L, label = "#투두"))
            add(Tag(id = 2L, label = "#메모"))
            add(Tag(id = 3L, label = "#일상 기록"))
        }
    }
}