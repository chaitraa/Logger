package com.Logger.domain.repository;

import com.Logger.domain.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserInfo,String>{

    UserInfo findByUserName(String userName);

}
