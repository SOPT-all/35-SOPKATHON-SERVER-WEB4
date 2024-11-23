package com.sopt.sopkathon.domain.fail.controller;

import com.sopt.sopkathon.common.response.ApiResponseUtil;
import com.sopt.sopkathon.common.response.BaseResponse;
import com.sopt.sopkathon.common.response.message.SuccessMessage;
import com.sopt.sopkathon.domain.fail.controller.dto.res.AllFailsRes;
import com.sopt.sopkathon.domain.fail.controller.dto.res.FailsRankList;
import com.sopt.sopkathon.domain.fail.controller.dto.res.MyAllFailsRes;
import com.sopt.sopkathon.domain.fail.service.FailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FailController {

    private final FailService failService;

    public FailController(FailService failService) {
        this.failService = failService;
    }

    @GetMapping("/fails")
    public ResponseEntity<BaseResponse<?>> getAllFails(
            @RequestHeader final Long userId
    ) {
        final AllFailsRes allFails = failService.getAllFails(userId);
        return ApiResponseUtil.success(SuccessMessage.OK, allFails);
    }

    @GetMapping("/fails/my")
    public ResponseEntity<BaseResponse<?>> getMyFails(
            @RequestHeader(name = "userId") final Long userId
    ) {
        final MyAllFailsRes myAllFailsRes = failService.getMyFails(userId);
        return ApiResponseUtil.success(SuccessMessage.OK, myAllFailsRes);
    }

    @GetMapping("fails/rank")
    public ResponseEntity<BaseResponse<?>> getAllFailsRank(
            @RequestHeader final Long userId
    ) {
        final FailsRankList failsRank = failService.getFailsRankList(userId);
        return ApiResponseUtil.success(SuccessMessage.OK, failsRank);
    }
}
