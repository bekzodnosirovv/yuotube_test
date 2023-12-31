package com.example.repository;

import com.example.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer>,
        PagingAndSortingRepository<SubscriptionEntity, Integer> {
}
