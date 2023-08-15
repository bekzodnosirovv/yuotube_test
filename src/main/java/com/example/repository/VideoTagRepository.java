package com.example.repository;

import com.example.entity.VideoTagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoTagRepository extends CrudRepository<VideoTagEntity, String>,
        PagingAndSortingRepository<VideoTagEntity, String> {
}
