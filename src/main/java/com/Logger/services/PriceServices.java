package com.Logger.services;

import com.Logger.domain.model.Price;
import com.Logger.domain.repository.PriceImpl;
import com.Logger.domain.repository.PriceRepository;
import com.Logger.utils.ParserUtility;
import com.Logger.utils.Response.PriceResponse;
import com.Logger.utils.RoomAvailabilityRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by technology on 11/4/17.
 */
@Service
public class PriceServices {

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    PriceImpl priceImpl;

    @Autowired
    ParserUtility parserUtility;

    public String saveprice(List<ObjectNode> node) {
        List<Price> priceResult;
        try {
            List<Price> priceList = parserUtility.createPriceObjectNode(node);
           priceResult=priceRepository.save(priceList);
        }
        catch (Exception mongoProblem)
        {
            throw new MongoException(mongoProblem.getMessage(),mongoProblem);
        }
        return priceResult.isEmpty()?"Cannot save Empty List!!":"Saved successfully";
    }

    public List<Price> getInfo() {
       return priceRepository.findAll();
    }

    public String deleteDemo() {
        priceRepository.deleteAll();
        return "deleted";
    }

    public List<PriceResponse> findByRequest(RoomAvailabilityRequest availabilityRequest)
    {
    return priceImpl.findByRequest(availabilityRequest);
    }

    public String saveprices(List<Price> price) {
        priceRepository.save(price);
        return "Saved Successfully";
    }
}
