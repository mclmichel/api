package com.codirect.codiappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
