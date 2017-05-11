package com.Logger.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by technology on 11/4/17.
 */
@Document
@Data
public class Price {
    @Id
    public String _id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ",timezone="GMT+5:30")
    public Date createdDate;
    public Long userId;
    public String userName;
    public Long uniqueId;
    public List<Hotels> hotels ;

}
