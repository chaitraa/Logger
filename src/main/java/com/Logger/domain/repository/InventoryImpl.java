package com.Logger.domain.repository;

import com.mongodb.BasicDBObject;
import com.Logger.domain.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class InventoryImpl {

    final MongoTemplate mongoTemplate;

    @Autowired
    public InventoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Inventory> findByHotelIdOrRoomIdAndDateRange(String startDate, String endDate, int hotelId, int roomId) {
        Aggregation agg;
        if (roomId != 0) {
            agg = newAggregation(
                    match(Criteria.where("hotels.rooms.availability.bookDate").gte(startDate).lte(endDate)
                            .andOperator(Criteria.where("hotels.hotelId").is(hotelId), Criteria.where("hotels.rooms.roomId").is(roomId))),
                    unwind("hotels"),
                    unwind("hotels.rooms"),
                    unwind("hotels.rooms.availability"),
                    match(Criteria.where("hotels.rooms.availability.bookDate").gte(startDate).lte(endDate)
                            .andOperator(Criteria.where("hotels.hotelId").is(hotelId), Criteria.where("hotels.rooms.roomId").is(roomId))),
                    group("accessKey","hotels.hotelId", "hotels.rooms.roomId")
                            .push("$hotels.rooms.availability").as("availability").push("$hotels.rooms.roomId").as("roomId"),
                    group().push(new BasicDBObject("roomId", "$roomId").append("availability", "$availability")).as("rooms")
                            .addToSet(new BasicDBObject("hotelIdNew", "$_id.hotelId")).as("hotelId"),
                    group().push(new BasicDBObject("hotelId", "$hotelId.hotelIdNew").append("rooms", "$rooms")).as("hotels")
            );
        } else {
             agg = newAggregation(
                    match(Criteria.where("hotels.rooms.availability.bookDate").gte(startDate).lte(endDate)
                            .andOperator(Criteria.where("hotels.hotelId").is(hotelId))),
                    unwind("hotels"),
                    unwind("hotels.rooms"),
                    unwind("hotels.rooms.availability"),
                    match(Criteria.where("hotels.rooms.availability.bookDate").gte(startDate).lte(endDate)
                            .andOperator(Criteria.where("hotels.hotelId").is(hotelId))),
                    group("accessKey", "hotels.hotelId", "hotels.rooms.roomId")
                            .push("$hotels.rooms.availability").as("availability").push("$hotels.rooms.roomId").as("roomId")
                            .push("$accesskey").as("accessKey").push("$channelId").as("channelId"),
                    group().push(new BasicDBObject("roomId", "$roomId").append("availability", "$availability")).as("rooms")
                            .addToSet(new BasicDBObject("hotelIdNew", "$_id.hotelId")).as("hotelId"),
                    group().push(new BasicDBObject("hotelId", "$hotelId.hotelIdNew").append("rooms", "$rooms")).as("hotels")
            );
        }
        AggregationResults<Inventory> results = mongoTemplate.aggregate(agg, Inventory.class, Inventory.class);
        List<Inventory> result = results.getMappedResults();
        return result;
    }
}