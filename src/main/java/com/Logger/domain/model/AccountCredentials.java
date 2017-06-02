package com.Logger.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class AccountCredentials {


    public String userName;
    public String password;
    @JsonSerialize(contentUsing = GrantedAuthoritySerializer.class)
    @JsonDeserialize(contentUsing = GrantedAuthorityDeserializer.class)
    public List<GrantedAuthority> roles;
}
