package com.calyx.mapper;

import com.calyx.dto.response.DailyLogEntryResponse;
import com.calyx.testutil.TestData;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DailyLogMapperTest {

    @Test
    public void toResponse_includesDisplayName() {
        DailyLogEntryResponse response = DailyLogMapper.toResponse(
                TestData.sampleProductLogEntry(),
                "Chicken breast"
        );

        assertEquals("Chicken breast", response.displayName());
        assertEquals("PRODUCT", response.entryType());
        assertEquals(LocalDate.of(2026, 6, 25), response.logDate());
        assertEquals(165, response.calories());
    }
}
