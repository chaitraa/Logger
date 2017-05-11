package com.Logger.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class Hotels{

    public Integer hotelId;
    public List<Rooms> rooms ;

}
