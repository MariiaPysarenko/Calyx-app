package com.calyx.repository;

import com.calyx.model.Bmi;
import java.util.List;

public interface BmiRepository {

    Bmi save(Bmi bmi);
    List<Bmi> findByUserId(Long userId);
    List<Bmi> findAll();
}
