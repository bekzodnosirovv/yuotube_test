package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity,String>,
        PagingAndSortingRepository<CommentEntity,String> {
}
