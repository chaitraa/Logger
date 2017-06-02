package com.Logger.utils;

import com.Logger.domain.model.*;
import com.Logger.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by technology on 19/4/17.
 */
@Service
public class ParserUtility {
    @Autowired
    Parser parser;

    @Autowired
    UserRepository userRepository;

    List<Inventory> inventoryList;

    List<Price> priceList;

    List<UserInfo> userInfoList;

    public List<Inventory> createInventoryObjectNode(List<ObjectNode> inventoryNode) {
        inventoryList = new LinkedList<>();

        for (ObjectNode node : inventoryNode) {
            Inventory inventory = new Inventory();

            inventory.createdDate = parser.getCurrentDate();
            inventory.uniqueId = parser.getLong("uniqueId", node);
            inventory.reservationId = parser.getLong("reservationId", node);
            inventory.revisionId = parser.getLong("revisionId", node);
            inventory.type = parser.getTypeValue("revisionId", "reservationId", node);

            ArrayNode hotelsNode = (ArrayNode) node.get("hotels");
            List<Hotels> hotels = new LinkedList<>();
            for (JsonNode hotelNode : hotelsNode) {
                Hotels hotel = new Hotels();
                hotel.hotelId = hotelNode.get("hotelId").intValue();

                List<Rooms> rooms = new LinkedList<>();
                ArrayNode roomsNode = (ArrayNode) hotelNode.get("rooms");
                for (JsonNode roomNode : roomsNode) {
                    Rooms room = new Rooms();
                    room.roomId = roomNode.get("roomId").intValue();

                    List<Availability> availabilities = new LinkedList<>();
                    ArrayNode availabilitiesNode = (ArrayNode) roomNode.get("availability");
                    for (JsonNode availabilityNode : availabilitiesNode) {
                        Availability availability = new Availability();
                        availability.free = availabilityNode.get("free").intValue();
                        availability.date = parser.getDate("date", availabilityNode);
                        availabilities.add(availability);
                    }
                    rooms.add(room);
                    room.availability = availabilities;
                }
                hotel.rooms = rooms;
                hotels.add(hotel);
                inventory.hotels = hotels;
            }
            inventoryList.add(inventory);
        }
        return inventoryList;
    }

    public List<Price> createPriceObjectNode(List<ObjectNode> priceNode) {
        priceList = new LinkedList<>();
        for (ObjectNode node : priceNode) {
            Price price = new Price();
            price.userId = parser.getLong("userId", node);
            price.userName = parser.getString("userName", node);
            price.createdDate = parser.getCurrentDate();
            price.uniqueId = parser.getLong("uniqueId", node);

            ArrayNode hotelsNode = (ArrayNode) node.get("hotels");
            List<Hotels> hotels = new LinkedList<>();
            for (JsonNode hotelNode : hotelsNode) {
                Hotels hotel = new Hotels();
                hotel.hotelId = hotelNode.get("hotelId").intValue();

                List<Rooms> rooms = new LinkedList<>();
                ArrayNode roomsNode = (ArrayNode) hotelNode.get("rooms");
                for (JsonNode roomNode : roomsNode) {
                    Rooms room = new Rooms();
                    room.roomId = Integer.parseInt(roomNode.get("roomId").textValue());

                    ArrayNode ratePlansNodeList = (ArrayNode) roomNode.get("rateplans");
                    List<RatePlans> ratePlans = new LinkedList<>();
                    for (JsonNode ratePlanNode : ratePlansNodeList) {
                        RatePlans ratePlan = new RatePlans();
                        ratePlan.rateplanId = Integer.parseInt(ratePlanNode.get("rateplanId").textValue());

                        List<PriceDetails> priceDetails = new LinkedList<>();
                        ArrayNode priceDetailsNodeList = (ArrayNode) ratePlanNode.get("priceDetails");
                        for (JsonNode priceDetailNode : priceDetailsNodeList) {
                            PriceDetails priceDetail = new PriceDetails();
                            priceDetail.date = parser.getDate("date", priceDetailNode);

                            // List<Prices> prices=new LinkedList<>();
                            JsonNode pricesNode = priceDetailNode.get("price");
                            Prices pricesObject = new Prices();
                            pricesObject.triple = pricesNode.get("Triple").decimalValue();
                            pricesObject.quad = pricesNode.get("Quad").decimalValue();
                            pricesObject.single = pricesNode.get("Single").decimalValue();
                            pricesObject.extraInfant = pricesNode.get("Extra Infant").decimalValue();
                            pricesObject.Double = pricesNode.get("Double").decimalValue();
                            pricesObject.extraAdult = pricesNode.get("Extra Adult").decimalValue();
                            pricesObject.extraChild = pricesNode.get("Extra Child").decimalValue();

                            priceDetail.price = pricesObject;
                            priceDetails.add(priceDetail);
                        }
                        ratePlan.priceDetails = priceDetails;
                        ratePlans.add(ratePlan);
                    }
                    room.rateplans = ratePlans;
                    rooms.add(room);

                }
                hotel.rooms = rooms;
                hotels.add(hotel);
            }
            price.hotels = hotels;
            priceList.add(price);
        }
        return priceList;
    }
}
  /*  public List<UserInfo> createUserInfoNode(ObjectNode userNode) throws MongoException{
        userInfoList=new LinkedList<>();
        UserInfo userInfo=new UserInfo();
        try {
            UserInfo userInfoExists = userRepository.findByUserName(userNode.get("userName").asText());
            if (userInfoExists == null) {
                userInfo.userName = parser.getString("userName", userNode);
                userInfo.password = parser.getString("password", userNode);
                userInfo.firstName = parser.getString("firstName", userNode);
                userInfo.lastName = parser.getString("lastName", userNode);
                userInfo.emailId = parser.getString("emailId", userNode);
                userInfoList.add(userInfo);
            }
        }catch(Exception mongoProblem)
        {
            throw new MongoException(mongoProblem.getMessage(),mongoProblem);
        }
        return userInfoList;
    }
}*/