package com.calyx.service.impl;

import com.calyx.dto.response.DailyCaloriesResponse;
import com.calyx.repository.DailyLogRepository;
import com.calyx.repository.UserRepository;
import com.calyx.testutil.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalorieServiceImplTest {

    @Mock
    private DailyLogRepository dailyLogRepository;

    @Mock
    private UserRepository userRepository;

    private CalorieServiceImpl calorieService;

    @Before
    public void setUp() {
        calorieService = new CalorieServiceImpl(dailyLogRepository, userRepository);
    }

    @Test
    public void getDailyCalories_sumsRepositoryTotal() {
        LocalDate date = LocalDate.of(2026, 6, 25);
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleUser()));
        when(dailyLogRepository.sumCaloriesByUserIdAndDate(1L, date)).thenReturn(900);

        DailyCaloriesResponse response = calorieService.getDailyCalories(1L, date);

        assertEquals(900, response.totalCalories());
        assertEquals(date, response.date());
    }

    @Test
    public void getDailyCalories_rejectsNullDate() {
        assertThrows(IllegalArgumentException.class,
                () -> calorieService.getDailyCalories(1L, null));
    }
}
