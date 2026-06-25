package com.calyx.controller;

import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.response.BmiResponse;
import com.calyx.service.BmiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BmiControllerTest {

    @Mock
    private BmiService bmiService;

    private BmiController bmiController;

    @Before
    public void setUp() {
        bmiController = new BmiController(bmiService);
    }

    @Test
    public void calculate_delegatesToService() {
        BmiRequest request = new BmiRequest(1L, 65.0, 168);
        when(bmiService.calculateAndSave(request)).thenReturn(new BmiResponse(23.07, "Normal weight"));

        BmiResponse response = bmiController.calculate(request);

        assertEquals("Normal weight", response.category());
    }
}
