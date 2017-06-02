package com.Logger.services;

import com.Logger.domain.model.Inventory;
import com.Logger.domain.model.Response;
import com.Logger.domain.repository.InventoryImpl;
import com.Logger.domain.repository.InventoryRepository;
import com.Logger.domain.repository.ResponseRepository;
import com.Logger.utils.ParserUtility;
import com.Logger.utils.Response.InventoryResponse;
import com.Logger.utils.RoomAvailabilityRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//import com.Logger.repository.InventoryImpl;
//import com.Logger.domain.repository.ResponseImpl;

@Service
public class InventoryServices {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryImpl inventoryImpl;

    @Autowired
    ParserUtility parserUtility;
/*
    @Autowired
    ResponseImpl responseImpl;*/

    @Autowired
    ResponseRepository responseRepository;

    public String deleteDemo() {
        inventoryRepository.deleteAll();
        return "Deleted succesfully";
    }
    public List<Inventory> getInfo() {
        return inventoryRepository.findAll();
    }

   /* public String saveInventory(List<Inventory> inventory) {
        inventoryRepository.insert(inventory);
        return "Saved succesfully";
    }*/
   public String saveInventory(List<ObjectNode> inventoryNode) throws MongoException{
       List<Inventory> inventoryResult;
       try {
           List<Inventory> inventories = parserUtility.createInventoryObjectNode(inventoryNode);
           inventoryResult = inventoryRepository.insert(inventories);
       }
       catch (Exception mongoProblem)
       {
           throw new MongoException(mongoProblem.getMessage(),mongoProblem);
       }
       return inventoryResult.isEmpty()?"Cannot save Empty List!!":"Saved successfully";
   }

    public List<InventoryResponse> findByRequest(RoomAvailabilityRequest availabilityRequest) {
        return inventoryImpl.findByRequest(availabilityRequest);
    }

    public String saveResponse(Response response) {
        responseRepository.save(response);
        return "Saved Successfully";
    }

      /*public List<Inventory>  updateById(Response response) {

        return responseImpl.updateById(response.uniqueId,response.status);
    }*/

    public List<Response> getResponse()
    {
        return responseRepository.findAll();
    }
}











