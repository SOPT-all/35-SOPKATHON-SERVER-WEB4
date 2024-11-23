package com.sopt.sopkathon.domain.emoji.controller;

import com.sopt.sopkathon.common.response.ApiResponseUtil;
import com.sopt.sopkathon.common.response.BaseResponse;
import com.sopt.sopkathon.common.response.message.SuccessMessage;
import com.sopt.sopkathon.domain.emoji.entity.EmojiType;
import com.sopt.sopkathon.domain.emoji.service.EmojiService;
import com.sopt.sopkathon.domain.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmojiController {

    private final EmojiService emojiService;
    private final UserRepository userRepository;


    public EmojiController(EmojiService emojiService, UserRepository userRepository) {
        this.emojiService = emojiService;
        this.userRepository = userRepository;
    }

    @PostMapping("/emoji/{failId}")
    public ResponseEntity<BaseResponse<?>> postEmoji(
            @RequestHeader(name = "userId") final Long userId,
            @PathVariable(name = "failId") final Long failId,
            @RequestParam(name = "emojiType") final EmojiType emojiType
    ) {
        emojiService.postEmoji(userId, failId, emojiType);
        return ApiResponseUtil.success(SuccessMessage.CREATED);
    }

    @DeleteMapping("/emoji/{failId}")
    public ResponseEntity<BaseResponse<?>> deleteEmoji(
            @RequestHeader final Long userId,
            @PathVariable final Long failId
    ) {
        emojiService.deleteEmoji(userId, failId);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }
}
