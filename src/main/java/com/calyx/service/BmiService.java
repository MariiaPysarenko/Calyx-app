package com.calyx.service;

import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.response.BmiResponse;

public interface BmiService {
    BmiResponse calculateAndSave(BmiRequest request);
}
