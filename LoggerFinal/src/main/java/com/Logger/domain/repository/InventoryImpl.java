package com.Logger.domain.repository;

import com.Logger.utils.CriteriaChecking;
import com.Logger.utils.DateUtils;
import com.Logger.utils.Response.InventoryResponse;
import com.Logger.utils.RoomAvailabilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class InventoryImpl {

    final MongoTemplate mongoTemplate;
    Aggregation agg;
    AggregationResults<InventoryResponse> results;
    List<InventoryResponse> result;
    List<Criteria> criteriaList;

    @Autowired
    public InventoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<InventoryResponse> findByRequest(RoomAvailabilityRequest availabilityRequest)
    {
        Criteria criteria= new Criteria();
        criteriaList = new ArrayList<Criteria>();

        CriteriaChecking.nullCheck(criteriaList,availabilityRequest);
        CriteriaChecking.checkForInventoryDateRange(criteriaList,availabilityRequest);
        CriteriaChecking.checkForHotelIdOrRoomIdOrRateplanId(criteriaList,availabilityRequest);
        CriteriaChecking.checkForReservationId(criteriaList,availabilityRequest);

        criteria = criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));

            agg = newAggregation(
                    match(criteria),
                    unwind("hotels"),
                    unwind("hotels.rooms"),
                    unwind("hotels.rooms.availability"),
                    match(criteria),
                    project("uniqueId", "createdDate", "type", "reservationId", "revisionId")
                            .and("hotels.hotelId").as("hotelId").and("hotels.rooms.roomId").as("roomId")
                            .and("hotels.rooms.availability.free").as("occupancy")
                            .and("hotels.rooms.availability.date").as("date"),
                    lookup("response", "uniqueId", "uniqueId", "responseField"),
                    unwind("responseField",true),
                    project("uniqueId","type","createdDate","reservationId","revisionId","hotelId","roomId","occupancy","date")
                          .and("responseField.status").applyCondition(ConditionalOperators.ifNull("responseField.status").then("N/A"))
            );
        results = mongoTemplate.aggregate(agg, "inventory", InventoryResponse.class);
        result= results.getMappedResults();
        return result;
    }
}

