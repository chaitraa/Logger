package com.Logger.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Component
public class RoomAvailabilityRequest {
    public Integer hotelId;
    public Integer roomId;
    public Integer rateplanId;
    public String startDate;
    public String endDate;
    public Long reservationId;
}