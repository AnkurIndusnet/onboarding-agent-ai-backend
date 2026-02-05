package com.example.onboardingAgent.onboardingAgent.hr.service;

import com.example.onboardingAgent.onboardingAgent.hr.dto.response.HiringTrendDTO;
import com.example.onboardingAgent.onboardingAgent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HrHiringTrendService {

    private final UserRepository userRepository;

    public List<HiringTrendDTO> getHiringTrend(int year) {

        Map<Integer, Long> monthCountMap = new HashMap<>();

        for (Object[] row : userRepository.countHiresByMonth(year)) {
            Integer month = (Integer) row[0];
            Long count = (Long) row[1];
            monthCountMap.put(month, count);
        }

        List<HiringTrendDTO> result = new ArrayList<>();

        for (int m = 1; m <= 12; m++) {
            result.add(
                    new HiringTrendDTO(
                            Month.of(m).getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                            monthCountMap.getOrDefault(m, 0L)
                    )
            );
        }

        return result;
    }
}
