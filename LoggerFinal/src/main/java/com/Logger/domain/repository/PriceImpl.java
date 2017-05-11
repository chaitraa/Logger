package com.Logger.domain.repository;

import com.Logger.domain.model.Price;
import com.Logger.utils.CriteriaChecking;
import com.Logger.utils.DateUtils;
import com.Logger.utils.Response.PriceResponse;
import com.Logger.utils.RoomAvailabilityRequest;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
/**
 * Created by technology on 12/4/17.
 */
@Repository
public class PriceImpl {
    final MongoTemplate mongoTemplate;

    Aggregation agg;

    @Autowired
    public PriceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    List<Criteria> criteriaList;

    public List<PriceResponse> findByRequest(RoomAvailabilityRequest availabilityRequest) {

        Criteria criteria = new Criteria();
        criteriaList = new ArrayList<Criteria>();

        CriteriaChecking.nullCheck(criteriaList, availabilityRequest);
        CriteriaChecking.checkForPriceDateRange(criteriaList, availabilityRequest);
        CriteriaChecking.checkForHotelIdOrRoomIdOrRateplanId(criteriaList, availabilityRequest);

        criteria = criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));

       /* Cond operator = ConditionalOperators.when("start")
                .eq("EARLY")
                .thenValueOf("deltastart.start")
                .otherwise("deltastart.end");*/


        agg = newAggregation(
                match(criteria),
                unwind("hotels"),
                unwind("hotels.rooms"),
                unwind("hotels.rooms.rateplans"),
                unwind("hotels.rooms.rateplans.priceDetails"),
                match(criteria),
                project("uniqueId", "createdDate","userName", "userId")
                        .and("hotels.hotelId").as("hotelId")
                        .and("hotels.rooms.roomId").as("roomId")
                        .and("hotels.rooms.rateplans.rateplanId").as("rateplanId")
                        .and("hotels.rooms.rateplans.priceDetails.price").as("price")
                        .and("hotels.rooms.rateplans.priceDetails.date").as("date"),
                lookup("response","uniqueId","uniqueId", "responseField"),
                //lookup("response", "uniqueId", "uniqueId", "responseField"),
                unwind("responseField",true),
                project("uniqueId", "userName", "createdDate", "userId", "rateplanId", "hotelId", "roomId", "price", "date")
                        .and("responseField.status").applyCondition(ConditionalOperators.ifNull("responseField.status").then("N/A"))
        );
        AggregationResults<PriceResponse> results = mongoTemplate.aggregate(agg, "price", PriceResponse.class);
        List<PriceResponse> result = results.getMappedResults();
        return result;

    }
}




















































 /* try {
            if ((hotelId != 0 && hotelId != null) && (roomId != 0 && roomId != null) && (rateplanId != 0 && rateplanId != null)) {
                agg = newAggregation(
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date")
                                .gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))
                                .orOperator(Criteria.where("hotels.hotelId").is(hotelId).orOperator(
                                        Criteria.where("hotels.rooms.roomId").is(roomId).orOperator(
                                        Criteria.where("hotels.rooms.rateplans.rateplanId").is(rateplanId))))),
                        unwind("hotels"),
                        unwind("hotels.rooms"),
                        unwind("hotels.rooms.rateplans"),
                        unwind("hotels.rooms.rateplans.priceDetails"),
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date")
                                .gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))
                                .orOperator(Criteria.where("hotels.hotelId").is(hotelId).orOperator(
                                        Criteria.where("hotels.rooms.roomId").is(roomId).orOperator(
                                                Criteria.where("hotels.rooms.rateplans.rateplanId").is(rateplanId))))),
                        group("_id","userName","userId","createdDate","hotels.hotelId", "hotels.rooms.roomId", "hotels.rooms.rateplans.rateplanId")
                                .push("$hotels.rooms.rateplans.priceDetails").as("priceDetails")
                                .push("$hotels.rooms.rateplans.rateplanId").as("rateplanId")
                                .push("$hotels.rooms.roomId").as("roomId"),
                        group("_id").push(new BasicDBObject("rateplanId", "$rateplanId").append("priceDetails", "$priceDetails")).as("rateplans")
                                .addToSet(new BasicDBObject("roomIdNew", "$roomId")).as("roomId"),
                        group("_id").push(new BasicDBObject("roomId", "$roomId.roomIdNew").append("rateplans", "$rateplans")).as("rooms"),
                               // .addToSet(new BasicDBObject("hotelIdNew", "$_id.hotelId")).as("hotelId"),
                        group("_id").push(new BasicDBObject("hotelId", "$_id.hotelId").append("rooms", "$rooms")).as("hotels"),
                        project("hotels").andInclude("_id._id","_id.userId","_id.userName","_id.createdDate")

                        );
            }
            else if ((hotelId != null && hotelId != 0) && (roomId != null && roomId != 0) && (rateplanId == null) ) {
                agg = newAggregation(
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date").gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))
                                .andOperator(Criteria.where("hotels.hotelId").is(hotelId),Criteria.where("hotels.rooms.roomId").is(roomId))),
                        unwind("hotels"),
                        unwind("hotels.rooms"),
                        unwind("hotels.rooms.rateplans"),
                        unwind("hotels.rooms.rateplans.priceDetails"),
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date").gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))
                                .andOperator(Criteria.where("hotels.hotelId").is(hotelId),Criteria.where("hotels.rooms.roomId").is(roomId))),
                        group("_id","userName","userId","createdDate","hotels.hotelId", "hotels.rooms.roomId", "hotels.rooms.rateplans.rateplanId")
                                .push("$hotels.rooms.rateplans.priceDetails").as("priceDetails")
                                .push("$hotels.rooms.rateplans.rateplanId").as("rateplanId")
                                .push("$hotels.rooms.roomId").as("roomId"),
                        group("_id").push(new BasicDBObject("rateplanId", "$rateplanId").append("priceDetails", "$priceDetails")).as("rateplans")
                                .addToSet(new BasicDBObject("roomIdNew", "$roomId")).as("roomId"),
                        group("_id").push(new BasicDBObject("roomId", "$roomId.roomIdNew").append("rateplans", "$rateplans")).as("rooms"),
                        // .addToSet(new BasicDBObject("hotelIdNew", "$_id.hotelId")).as("hotelId"),
                        group("_id").push(new BasicDBObject("hotelId", "$_id.hotelId").append("rooms", "$rooms")).as("hotels"),
                        project("hotels").andInclude("_id._id","_id.userId","_id.userName","_id.createdDate")

                );
            }
            else if (hotelId != null && hotelId != 0) {
                agg = newAggregation(
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date").gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))
                                .andOperator(Criteria.where("hotels.hotelId").is(hotelId))),
                        unwind("hotels"),
                        unwind("hotels.rooms"),
                        unwind("hotels.rooms.rateplans"),
                        unwind("hotels.rooms.rateplans.priceDetails"),
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date").gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))
                                .andOperator(Criteria.where("hotels.hotelId").is(hotelId))),
                        group("_id","userName","userId","createdDate","hotels.hotelId", "hotels.rooms.roomId", "hotels.rooms.rateplans.rateplanId")
                                .push("$hotels.rooms.rateplans.priceDetails").as("priceDetails")
                                .push("$hotels.rooms.rateplans.rateplanId").as("rateplanId")
                                .push("$hotels.rooms.roomId").as("roomId"),
                        group("_id").push(new BasicDBObject("rateplanId", "$rateplanId").append("priceDetails", "$priceDetails")).as("rateplans")
                                .addToSet(new BasicDBObject("roomIdNew", "$roomId")).as("roomId"),
                        group("_id").push(new BasicDBObject("roomId", "$roomId.roomIdNew").append("rateplans", "$rateplans")).as("rooms"),
                        // .addToSet(new BasicDBObject("hotelIdNew", "$_id.hotelId")).as("hotelId"),
                        group("_id").push(new BasicDBObject("hotelId", "$_id.hotelId").append("rooms", "$rooms")).as("hotels"),
                        project("hotels").andInclude("_id._id","_id.userId","_id.userName","_id.createdDate")

                );
            }
                else if ((hotelId == 0 ||  hotelId== null) && (roomId == 0 || roomId == null) && (rateplanId == 0 || rateplanId==null)) {
                agg = newAggregation(
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date")
                                .gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))),
                        unwind("hotels"),
                        unwind("hotels.rooms"),
                        unwind("hotels.rooms.rateplans"),
                        unwind("hotels.rooms.rateplans.priceDetails"),
                        match(Criteria.where("hotels.rooms.rateplans.priceDetails.date")
                                .gte(DateUtils.getDate(startDate, DateUtils.DB_FORMAT_DATETIME)).lte(DateUtils.getDate(endDate, DateUtils.DB_FORMAT_DATETIME))),
                        group("_id","userName","userId","createdDate","hotels.hotelId", "hotels.rooms.roomId", "hotels.rooms.rateplans.rateplanId")
                                .push("$hotels.rooms.rateplans.priceDetails").as("priceDetails")
                                .push("$hotels.rooms.rateplans.rateplanId").as("rateplanId")
                                .push("$hotels.rooms.roomId").as("roomId"),
                        group("_id").push(new BasicDBObject("rateplanId", "$rateplanId").append("priceDetails", "$priceDetails")).as("rateplans")
                                .addToSet(new BasicDBObject("roomIdNew", "$roomId")).as("roomId"),
                        group("_id").push(new BasicDBObject("roomId", "$roomId.roomIdNew").append("rateplans", "$rateplans")).as("rooms"),
                        // .addToSet(new BasicDBObject("hotelIdNew", "$_id.hotelId")).as("hotelId"),
                        group("_id").push(new BasicDBObject("hotelId", "$_id.hotelId").append("rooms", "$rooms")).as("hotels"),
                        project("hotels").andInclude("_id._id","_id.userId","_id.userName","_id.createdDate")

                );
            }*/