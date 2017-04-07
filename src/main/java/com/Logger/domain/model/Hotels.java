package com.Logger.domain.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Data
public class Hotels{

    int hotelId;
    public List<Rooms> rooms ;

}
