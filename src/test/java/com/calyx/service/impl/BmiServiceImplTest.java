package com.calyx.service.impl;

import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.response.BmiResponse;
import com.calyx.exception.ValidationException;
import com.calyx.model.Bmi;
import com.calyx.repository.BmiRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BmiServiceImplTest {

    @Mock
    private BmiRepository bmiRepository;

    private BmiServiceImpl bmiService;

    @Before
    public void setUp() {
        bmiService = new BmiServiceImpl(bmiRepository);
    }

    @Test
    public void calculateAndSave_normalWeight() {
        when(bmiRepository.save(any(Bmi.class))).thenAnswer(invocation -> {
            Bmi bmi = invocation.getArgument(0);
            bmi.setId(1L);
            return bmi;
        });

        BmiResponse response = bmiService.calculateAndSave(new BmiRequest(1L, 65.0, 168));

        assertEquals(23.03, response.bmiValue(), 0.01);
        assertEquals("Normal weight", response.category());
    }

    @Test
    public void calculateAndSave_underweight() {
        when(bmiRepository.save(any(Bmi.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BmiResponse response = bmiService.calculateAndSave(new BmiRequest(1L, 45.0, 170));

        assertEquals("Underweight", response.category());
    }

    @Test
    public void calculateAndSave_rejectsInvalidWeight() {
        assertThrows(ValidationException.class,
                () -> bmiService.calculateAndSave(new BmiRequest(1L, 0, 170)));
    }
}
