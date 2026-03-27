package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.LikePostEntity
import com.dieang.energym.data.remote.dto.response.LikeResponseDto

// Response → Entity
fun LikeResponseDto.toEntity() = LikePostEntity(
    postId = postId,
    usuarioId = usuarioId
)