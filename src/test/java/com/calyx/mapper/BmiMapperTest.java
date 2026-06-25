package com.calyx.mapper;

import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.response.BmiResponse;
import com.calyx.model.Bmi;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class BmiMapperTest {

    @Test
    public void toEntity_andToResponse_roundTrip() {
        BmiRequest request = new BmiRequest(1L, 65.0, 168);

        Bmi entity = BmiMapper.toEntity(request, 23.07, "Normal weight");
        entity.setId(1L);

        BmiResponse response = BmiMapper.toResponse(entity);

        assertEquals(23.07, response.bmiValue(), 0.001);
        assertEquals("Normal weight", response.category());
    }

    @Test
    public void toEntity_setsUserAndWeight() {
        Bmi bmi = BmiMapper.toEntity(new BmiRequest(1L, 70.0, 175), 22.86, "Normal weight");

        assertEquals(1L, bmi.getUserId().longValue());
        assertEquals(70.0, bmi.getWeightKg(), 0.001);
    }
}
