package com.Logger.domain.model;
import lombok.Data;
import java.util.List;
@Data
public class Rooms
{
    int roomId;
    public List<Availability> availability;
}
