package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,String>,
        PagingAndSortingRepository<EmailHistoryEntity,String> {
}
