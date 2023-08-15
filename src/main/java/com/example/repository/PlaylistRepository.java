package com.example.repository;

import com.example.entity.PlaylistEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends CrudRepository<PlaylistEntity, String>,
        PagingAndSortingRepository<PlaylistEntity, String> {
}
