package com.Logger.utils;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * Created by technology on 8/5/17.
 */
public class CriteriaChecking {

    public static void nullCheck(List<Criteria> criteriaList, RoomAvailabilityRequest request) {
        if (request.startDate == null && request.endDate == null && (request.hotelId == null || request.hotelId == 0)
                && (request.roomId == null || request.roomId == 0) && request.reservationId == null && (request.rateplanId==null || request.rateplanId==0) ) {
            criteriaList.add(new Criteria());
        }
    }

    public static void checkForInventoryDateRange(List<Criteria> criteriaList, RoomAvailabilityRequest request) {
        if(request.startDate != null && request.endDate != null)
        {
            criteriaList.add(Criteria.where("hotels.rooms.availability.date")
                    .gte(DateUtils.getDate(request.startDate, DateUtils.DB_FORMAT_DATETIME))
                    .lte(DateUtils.getDate(request.endDate, DateUtils.DB_FORMAT_DATETIME)));
        }
    }

    public static void checkForPriceDateRange(List<Criteria> criteriaList, RoomAvailabilityRequest request) {
        if(request.startDate != null && request.endDate != null)
        {
            criteriaList.add(Criteria.where("hotels.rooms.rateplans.priceDetails.date")
                    .gte(DateUtils.getDate(request.startDate, DateUtils.DB_FORMAT_DATETIME))
                    .lte(DateUtils.getDate(request.endDate, DateUtils.DB_FORMAT_DATETIME)));
        }
    }

    public static void checkForHotelIdOrRoomIdOrRateplanId(List<Criteria> criteriaList, RoomAvailabilityRequest request) {
        if((request.hotelId != null && request.hotelId != 0) && (request.roomId != null && request.roomId != 0) && (request.rateplanId!=null && request.rateplanId!=0))
        {
            criteriaList.add(Criteria.where("hotels.hotelId").is(request.hotelId).and("hotels.rooms.roomId").is(request.roomId).and("hotels.rooms.rateplans.rateplanId").is(request.rateplanId));
        }
        else if((request.hotelId != null && request.hotelId != 0) && (request.roomId != null && request.roomId != 0))
        {
            criteriaList.add(Criteria.where("hotels.hotelId").is(request.hotelId).and("hotels.rooms.roomId").is(request.roomId));
        }
        else if(request.hotelId != null && request.hotelId != 0) {
            criteriaList.add(Criteria.where("hotels.hotelId").is(request.hotelId));
        }
    }

    public static void checkForReservationId(List<Criteria> criteriaList, RoomAvailabilityRequest request)
    {
        if (request.reservationId != null) {
            criteriaList.add(Criteria.where("reservationId").is(request.reservationId));
        }
    }
}
