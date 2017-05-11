package com.Logger.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Rooms
{

    public Integer roomId;
    public List<RatePlans> rateplans ;
    public List<Availability> availability;
}
