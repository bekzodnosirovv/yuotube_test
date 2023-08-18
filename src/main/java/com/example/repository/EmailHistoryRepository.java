package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, String>,
        PagingAndSortingRepository<EmailHistoryEntity, String> {
    Page<EmailHistoryEntity> findAllByToEmail(String email, Pageable pageable);

    Page<EmailHistoryEntity> findAllByCreatedDateBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<EmailHistoryEntity> findAllByToEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to, Pageable pageable);

    @Query("select count(e) from EmailHistoryEntity as e where e.toEmail=:email and e.createdDate>=:date and e.visible=true ")
    int countSenderEmail(@Param("email") String email,
                         @Param("date") LocalDateTime dateTime);
}
