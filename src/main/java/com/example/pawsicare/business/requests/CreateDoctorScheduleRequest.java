package com.example.pawsicare.business.requests;

import com.example.pawsicare.domain.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDoctorScheduleRequest {
    private String token;
    private DayOfWeek startDay;
    private DayOfWeek endDay;
    private String startHour;
    private String endHour;

}
