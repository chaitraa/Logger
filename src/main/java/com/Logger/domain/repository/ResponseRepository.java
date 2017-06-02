package com.Logger.domain.repository;

import com.Logger.domain.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by technology on 4/5/17.
 */
@Repository
public interface ResponseRepository extends MongoRepository<Response,String> {
}

