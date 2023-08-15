package com.example.repository;

import com.example.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer>,
        PagingAndSortingRepository<TagEntity, Integer> {
}
