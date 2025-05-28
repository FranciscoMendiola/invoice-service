package com.invoice.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.invoice.api.entity.CartItem;

@Repository
public interface RepoCartItem extends MongoRepository<CartItem, String> {

    @Query("{ 'userId': ?0 }")
    public List<CartItem> findByUserId(@Param("userId") Integer userId);

    @Query(value = "{ 'userId': ?0 }", delete = true)
    public void deleteByUserId(@Param("userId") Integer userId);
    
}
