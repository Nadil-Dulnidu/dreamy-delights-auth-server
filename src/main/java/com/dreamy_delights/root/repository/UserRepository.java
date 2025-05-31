package com.dreamy_delights.root.repository;

import com.dreamy_delights.root.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * find user object by id
     *
     * @param username username
     * @return found user object
     */
    UserEntity findByUsername(String username);
}
