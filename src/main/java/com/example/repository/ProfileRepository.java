package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>,
        PagingAndSortingRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findAllByEmailAndVisibleTrue(String email);

    Optional<ProfileEntity> findAllByIdAndVisibleTrue(Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=:id ")
    void delete(@Param("id") Integer profileId);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status=:status where id=:id and visible=true ")
    void updateStatus(@Param("status") ProfileStatus profileStatus,
                      @Param("id") Integer profileId);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set password=:password where id=:id and visible=true ")
    void changePassword(@Param("id") Integer id,
                        @Param("password") String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set email=:email where id=:id and visible=true ")
    void updateEmail(@Param("id") Integer id,
                     @Param("email") String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name=:name, surname=:surname where id=:id and visible=true ")
    void updateDetail(@Param("id") Integer id,
                      @Param("name") String name,
                      @Param("surname") String surname);
}
