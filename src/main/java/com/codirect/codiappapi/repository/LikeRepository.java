package com.codirect.codiappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

}
