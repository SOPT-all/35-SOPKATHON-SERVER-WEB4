package com.sopt.sopkathon.domain.fail.controller;

import com.sopt.sopkathon.common.response.ApiResponseUtil;
import com.sopt.sopkathon.common.response.BaseResponse;
import com.sopt.sopkathon.common.response.message.SuccessMessage;
import com.sopt.sopkathon.domain.fail.controller.dto.res.AllFailsRes;
import com.sopt.sopkathon.domain.fail.service.FailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FailController {

    private final FailService failService;

    public FailController(FailService failService) {
        this.failService = failService;
    }

    @GetMapping("/fails")
    public ResponseEntity<BaseResponse<?>> getAllFails() {
        final AllFailsRes getAllFails = failService.getAllFails();
        return ApiResponseUtil.success(SuccessMessage.OK, getAllFails);
    }
}
