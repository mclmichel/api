package com.codirect.codiappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
