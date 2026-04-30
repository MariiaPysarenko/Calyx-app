package com.calyx.controller;

import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.response.BmiResponse;
import com.calyx.service.BmiService;

public class BmiController {

    private final BmiService bmiService;

    public BmiController(BmiService bmiService) {
        this.bmiService = bmiService;
    }

    public BmiResponse calculate(BmiRequest request) {
        return bmiService.calculateAndSave(request);
    }
}