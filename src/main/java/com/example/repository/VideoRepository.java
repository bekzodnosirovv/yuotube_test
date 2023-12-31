package com.example.repository;

import com.example.entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<VideoEntity, String>,
        PagingAndSortingRepository<VideoEntity, String> {
}
