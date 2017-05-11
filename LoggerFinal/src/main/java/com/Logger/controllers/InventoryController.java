package com.Logger.controllers;

import com.Logger.domain.model.Inventory;
import com.Logger.domain.model.Response;
import com.Logger.utils.Response.InventoryResponse;
import com.Logger.utils.RoomAvailabilityRequest;
import com.Logger.services.InventoryServices;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/")
@RestController
public class InventoryController {

    @Autowired
    InventoryServices inventoryServices;

    @RequestMapping(value = "/deleteInventoryDemo", method = RequestMethod.POST)
    public String deleteDemo() {

        return inventoryServices.deleteDemo();
    }

    /*@RequestMapping(value = "/api/logger/cm/inventory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveInventory(@RequestBody List<Inventory> inventory) {
        return inventoryServices.saveInventory(inventory);
    }*/

    @RequestMapping(value = "/api/logger/cm/inventory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveInventory(@RequestBody List<ObjectNode> inventoryNode) {
        return inventoryServices.saveInventory(inventoryNode);
    }

    @RequestMapping(value = "/api/logger/cm/inventory", method = RequestMethod.GET)
    public List<Inventory> getInfo() {
        return inventoryServices.getInfo();
    }


    @RequestMapping(value = "/api/logger/cm/inventory/roomAvailability", method = RequestMethod.POST)
    public List<InventoryResponse> findByRequest(@RequestBody RoomAvailabilityRequest availabilityRequest) {
        return inventoryServices.findByRequest(availabilityRequest);
    }

    @RequestMapping(value = "/api/logger/crs/response/status", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveResponse(@RequestBody Response response) {
        return inventoryServices.saveResponse(response);
    }

    @RequestMapping(value = "/api/logger/crs/response/status", method = RequestMethod.GET)
    public List<Response> getResponse() {
        return inventoryServices.getResponse();
    }

    /* @RequestMapping(value = "/api/logger/crs/response/status/Id", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Inventory>  updateById(@RequestBody Response response) {
        return inventoryServices.updateById(response);
    }*/

}