package com.codirect.codiappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.StoryLeadView;

@Repository
public interface StoryLeadViewRepository extends JpaRepository<StoryLeadView, Long> {

}
