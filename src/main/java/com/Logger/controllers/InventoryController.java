package com.Logger.controllers;
import com.Logger.domain.model.Inventory;
import com.Logger.utils.RoomAvailabilityRequest;
import com.Logger.services.InventoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/")
@RestController
public class InventoryController {
    @Autowired
    InventoryServices inventoryServices;

    @RequestMapping(value = "/deleteDemo", method = RequestMethod.POST)
    public String deleteDemo() {

        return inventoryServices.deleteDemo();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveInventory(@RequestBody List<Inventory> inventory) {
        return inventoryServices.saveInventory(inventory);
    }

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public List<Inventory> getInfo() {
        return inventoryServices.getInfo();
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public List<Inventory> findByHotelIdOrRoomIdAndDateRange(@RequestBody RoomAvailabilityRequest availabilityRequest)
    {
        return inventoryServices.findByHotelIdOrRoomIdAndDateRange(availabilityRequest.startDate,availabilityRequest.endDate, availabilityRequest.hotelId,availabilityRequest.roomId);
    }
}