package com.Logger.domain.repository;

import com.Logger.domain.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory,String>
{
   /*@Query( value = "{ $and: [{'hotels.room.availability.bookingDate' : ?0},{'hotels.hotelId' : ?1} ]}", fields = "{hotels.room.availability.free:1}")
    Inventory findByHotelIdAndDate(String bookDate, int hotelId);

    @Query( value = "{ 'hotels.hotelId' : ?0}")
    Inventory findByHotelId(int hotelId);*/
}

