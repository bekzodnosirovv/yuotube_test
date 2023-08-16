package com.example.repository;

import com.example.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>,
        PagingAndSortingRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findAllByIdAndVisibleTrue(Integer categoryId);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set name=:name where id=:categoryId and visible=true ")
    int update(@Param("categoryId") Integer categoryId,
                @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible=false where id=:categoryId")
    void delete(@Param("categoryId") Integer categoryId);

    List<CategoryEntity> findAllByVisibleTrue();
}
