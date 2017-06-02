package com.Logger.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
@Data
@Document(collection = "user")
public class UserInfo {
    @Id
    public String _id;
    public String userName;
    public String password;
    public String firstName;
    public String lastName;
    public String emailId;
    @JsonSerialize(contentUsing = GrantedAuthoritySerializer.class)
    @JsonDeserialize(contentUsing = GrantedAuthorityDeserializer.class)
    public List<GrantedAuthority> roles;


}