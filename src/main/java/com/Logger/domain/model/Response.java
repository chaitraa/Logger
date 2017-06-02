package com.Logger.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by technology on 4/5/17.
 */
@Document(collection = "response")
public class Response {
    @Id
    String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ",timezone="GMT+5:30")
    public Date createdDate=new Date();
    public  Long uniqueId;
    public String status;
}
