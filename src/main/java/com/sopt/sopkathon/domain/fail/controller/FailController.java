package com.sopt.sopkathon.domain.fail.controller;

import com.sopt.sopkathon.common.response.ApiResponseUtil;
import com.sopt.sopkathon.common.response.BaseResponse;
import com.sopt.sopkathon.common.response.message.SuccessMessage;
import com.sopt.sopkathon.domain.fail.controller.dto.req.FailCreateReq;
import com.sopt.sopkathon.domain.fail.controller.dto.res.AllFailsRes;
import com.sopt.sopkathon.domain.fail.controller.dto.res.DetailFailInfo;
import com.sopt.sopkathon.domain.fail.controller.dto.res.FailRankList;
import com.sopt.sopkathon.domain.fail.controller.dto.res.MyAllFailsRes;
import com.sopt.sopkathon.domain.fail.service.FailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        final FailRankList failsRank = failService.getFailsRankList(userId);  //entity 수정해야됨
        return ApiResponseUtil.success(SuccessMessage.OK, failsRank);
    }

    @GetMapping("fail/{failId}/detail")
    public ResponseEntity<BaseResponse<?>> getDetailFails(
            @RequestHeader(name = "userId") final Long userId,
            @PathVariable(name = "failId") final Long failId
    ) {
        final DetailFailInfo detailFailInfo = failService.getDetailFailInfo(userId, failId);
        return ApiResponseUtil.success(SuccessMessage.OK, detailFailInfo);
    }

    @PostMapping("/fail")
    public ResponseEntity<BaseResponse<?>> createFail(
            @RequestHeader(name = "userId") final Long userId,
            @RequestBody final FailCreateReq failCreateReq
            ) {
        failService.createFail(userId, failCreateReq.content(), failCreateReq.backgroundType());
        return ApiResponseUtil.success(SuccessMessage.OK);
    }
}
