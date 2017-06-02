package com.Logger.domain.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Document
@Getter
@Setter
public class Inventory {
    @Id
    @Field("accessKey")
    String accessKey;
    int channelId;
    public List<Hotels> hotels ;
}


















