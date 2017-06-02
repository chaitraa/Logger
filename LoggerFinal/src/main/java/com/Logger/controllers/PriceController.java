package com.Logger.controllers;

import com.Logger.domain.model.Price;
import com.Logger.services.PriceServices;
import com.Logger.utils.Response.PriceResponse;
import com.Logger.utils.RoomAvailabilityRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by technology on 11/4/17.
 */
@RestController
@RequestMapping("/")
public class PriceController {
    @Autowired
    PriceServices priceServices;

    @RequestMapping(value = "/api/logger/cm/price", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String savePrice(@RequestBody List<ObjectNode> price)
    {
        return priceServices.saveprice(price);
    }

    @RequestMapping(value = "/api/logger/cm/prices", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String savePrices(@RequestBody List<Price> price)
    {
        return priceServices.saveprices(price);
    }

    @RequestMapping(value = "/api/logger/cm/price/filter", method = RequestMethod.POST)
    public List<PriceResponse> findByRequest(@RequestBody RoomAvailabilityRequest availabilityRequest)
    {
        return priceServices.findByRequest(availabilityRequest);
    }

    @RequestMapping(value = "/api/logger/cm/price", method = RequestMethod.GET)
    public List<Price> getInfo() {
        return priceServices.getInfo();
    }

    @RequestMapping(value = "/deleteDemo", method = RequestMethod.POST)
    public String deleteDemo() {
        return priceServices.deleteDemo();
    }

}
