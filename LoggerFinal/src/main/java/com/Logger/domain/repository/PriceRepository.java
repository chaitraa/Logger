package com.Logger.domain.repository;

import com.Logger.domain.model.Price;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by technology on 11/4/17.
 */
@Repository
public interface PriceRepository extends MongoRepository<Price, String> {
}
