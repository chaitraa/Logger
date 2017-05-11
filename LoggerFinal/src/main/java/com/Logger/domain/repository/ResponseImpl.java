package com.Logger.domain.repository;

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
public class ResponseImpl {
    final MongoTemplate mongoTemplate;
    Aggregation agg;

    @Autowired
    public ResponseImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Inventory> updateById(Long uniqueId, String status) {
       /* Query query = Query.query(Criteria.where("uniqueId").is(uniqueId));
        Update update = new Update();
        update.set("status", status);
        update.getUpdateObject();
        Inventory result = mongoTemplate.findAndModify(query, update, Inventory.class);
        return result;
*/      //DBCollection coll=mongoTemplate.getCollection("response");
        agg = newAggregation(
                Aggregation.match(Criteria.where("uniqueId").is(uniqueId)),
                //sort(Sort.Direction.ASC, "uniqueId"),
                lookup("response", "uniqueId", "uniqueId", "responseField"),
                unwind("responseField"),
                project("uniqueId","responseField.status","type","createdDate","reservationId","revisionId","hotels")
        );

        //Convert the aggregation result into a List
        AggregationResults<Inventory> groupResults = mongoTemplate.aggregate(agg, "inventory",Inventory.class);

        List<Inventory> result = groupResults.getMappedResults();

        return result;
    }


}
