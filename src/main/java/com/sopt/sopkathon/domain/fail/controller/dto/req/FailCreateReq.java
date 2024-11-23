package com.sopt.sopkathon.domain.fail.controller.dto.req;

import com.sopt.sopkathon.domain.fail.entity.BackgroundType;

public record FailCreateReq(
        String content,
        BackgroundType backgroundType
) {
}
