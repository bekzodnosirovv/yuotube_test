package com.example.repository;

import com.example.entity.ChannelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends CrudRepository<ChannelEntity, String>,
        PagingAndSortingRepository<ChannelEntity, String> {
}
