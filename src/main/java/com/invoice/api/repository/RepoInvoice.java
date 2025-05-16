package com.invoice.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.invoice.api.entity.Invoice;

@Repository
public interface RepoInvoice extends MongoRepository<Invoice, String> {

	@Query("{ 'user_id': ?0 }")
	List<Invoice> findByUser_Id(@Param("user_id") Integer user_id);
}
