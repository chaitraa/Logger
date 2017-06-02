package com.Logger.services;

import com.Logger.domain.model.Inventory;
import com.Logger.domain.repository.InventoryImpl;
import com.Logger.domain.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryServices {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryImpl inventory;

    public String deleteDemo() {
        inventoryRepository.deleteAll();
        return "Deleted succesfully";
    }
    public List<Inventory> getInfo() {
        return inventoryRepository.findAll();
    }

    public String saveInventory(List<Inventory> inventory) {
        inventoryRepository.insert(inventory);
        return "Saved succesfully";
    }
    public List<Inventory> findByHotelIdOrRoomIdAndDateRange(String startDate, String endDate, int hotelId,int roomId) {
        return inventory.findByHotelIdOrRoomIdAndDateRange(startDate,endDate, hotelId,roomId);  }
}











