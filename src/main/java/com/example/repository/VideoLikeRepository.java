package com.example.repository;

import com.example.entity.VideoLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity,String>,
        PagingAndSortingRepository<VideoLikeEntity,String> {
}
