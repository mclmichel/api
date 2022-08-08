package com.codirect.codiappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.LogActivity;

@Repository
public interface LogActivityRepository extends JpaRepository<LogActivity, Long> {

}
