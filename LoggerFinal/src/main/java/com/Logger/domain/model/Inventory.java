package com.Logger.domain.model;
import com.Logger.utils.TypeValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Document(collection = "inventory")
@Data
public class Inventory {
    @Id
    public String _id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ",timezone="GMT+5:30")
    public Date createdDate;
    public Long uniqueId;
    //public String status;
    public Long reservationId;
    public Long revisionId;
    public TypeValue type;
    public List<Hotels> hotels ;
}


















