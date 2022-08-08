package com.codirect.codiappapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.User;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

	public Optional<User> findByEmail(String email, EntityGraph entityGraph);
	
}
