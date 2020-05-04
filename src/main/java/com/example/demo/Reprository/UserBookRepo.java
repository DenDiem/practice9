package com.example.demo.Reprository;

import com.example.demo.Entity.UserBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserBookRepo extends JpaRepository<UserBookEntity, Integer> {
    List<UserBookEntity> findAllByUseridLike(String userid);
    @Modifying
    @Query("delete from UserBookEntity t where t.id = ?1")
    void delete(Integer entityId);
}