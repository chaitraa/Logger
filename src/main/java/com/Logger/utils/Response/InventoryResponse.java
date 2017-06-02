package com.Logger.utils.Response;

import com.Logger.utils.TypeValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by technology on 20/4/17.
 */
@Data
public class InventoryResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yy-MM-dd")
    Date date;
    Long reservationId;
    Long revisionId;
    Integer hotelId;
    Integer roomId;
    Integer occupancy;
    Long uniqueId;
    String status;
    TypeValue type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ",timezone="GMT+5:30")
    Date createdDate;

    @JsonSetter
    public void setStatus(String status) {
        this.status= status==null?"N/A":status;
    }
}
