package com.example.repository;

import com.example.entity.TagEntity;
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
public interface TagRepository extends CrudRepository<TagEntity, Integer>,
        PagingAndSortingRepository<TagEntity, Integer> {
    Optional<TagEntity> findAllByIdAndVisibleTrue(Integer tagId);

    @Transactional
    @Modifying
    @Query("update TagEntity set name=:name where id=:tagId and visible=true ")
    int update(@Param("tagId") Integer tagId,
                @Param("name") String name);
    @Transactional
    @Modifying
    @Query("update TagEntity set visible=false where id=:tagId")
    void delete(@Param("tagId")Integer tagId);

    List<TagEntity> findAllByVisibleTrue();
}
