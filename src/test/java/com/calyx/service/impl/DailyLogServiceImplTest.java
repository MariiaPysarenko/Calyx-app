package com.calyx.service.impl;

import com.calyx.dto.request.DailyLogProductRequest;
import com.calyx.dto.request.DailyLogMealRequest;
import com.calyx.dto.response.DailyLogEntryResponse;
import com.calyx.exception.ValidationException;
import com.calyx.model.DailyLogEntry;
import com.calyx.repository.DailyLogRepository;
import com.calyx.repository.MealRepository;
import com.calyx.repository.ProductRepository;
import com.calyx.repository.UserRepository;
import com.calyx.testutil.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DailyLogServiceImplTest {

    @Mock
    private DailyLogRepository dailyLogRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    private DailyLogServiceImpl dailyLogService;

    @Before
    public void setUp() {
        dailyLogService = new DailyLogServiceImpl(
                dailyLogRepository, productRepository, mealRepository, userRepository
        );
    }

    @Test
    public void logProduct_persistsProductEntry() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleUser()));
        when(productRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleProduct()));
        when(dailyLogRepository.save(any(DailyLogEntry.class))).thenAnswer(invocation -> {
            DailyLogEntry entry = invocation.getArgument(0);
            entry.setId(1L);
            return entry;
        });

        DailyLogEntryResponse response = dailyLogService.logProduct(
                new DailyLogProductRequest(1L, 1L, 100.0, LocalDate.of(2026, 6, 25))
        );

        assertEquals("Chicken breast", response.displayName());
        assertEquals("PRODUCT", response.entryType());
        assertEquals(165, response.calories());
    }

    @Test
    public void logMeal_persistsMealEntry() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleUser()));
        when(mealRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleMeal()));
        when(dailyLogRepository.save(any(DailyLogEntry.class))).thenAnswer(invocation -> {
            DailyLogEntry entry = invocation.getArgument(0);
            entry.setId(2L);
            return entry;
        });

        DailyLogEntryResponse response = dailyLogService.logMeal(
                new DailyLogMealRequest(1L, 1L, 1.0, LocalDate.of(2026, 6, 25))
        );

        assertEquals("Chicken rice bowl", response.displayName());
        assertEquals("MEAL", response.entryType());
        assertEquals(482, response.calories());
    }

    @Test
    public void logProduct_rejectsInvalidGrams() {
        assertThrows(ValidationException.class, () -> dailyLogService.logProduct(
                new DailyLogProductRequest(1L, 1L, 0, LocalDate.now())
        ));
    }

    @Test
    public void getLogForDate_resolvesDisplayNames() {
        when(dailyLogRepository.findByUserIdAndDate(1L, LocalDate.of(2026, 6, 25)))
                .thenReturn(List.of(TestData.sampleProductLogEntry()));
        when(productRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleProduct()));

        List<DailyLogEntryResponse> entries = dailyLogService.getLogForDate(
                1L, LocalDate.of(2026, 6, 25)
        );

        assertEquals(1, entries.size());
        assertEquals("Chicken breast", entries.get(0).displayName());
    }
}
