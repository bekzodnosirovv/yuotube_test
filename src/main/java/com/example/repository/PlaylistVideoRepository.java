package com.example.repository;

import com.example.entity.PlaylistVideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistVideoRepository extends CrudRepository<PlaylistVideoEntity,String>,
        PagingAndSortingRepository<PlaylistVideoEntity,String> {
}
