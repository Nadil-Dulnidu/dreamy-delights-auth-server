package com.dreamy_delights.root.repository;

import com.dreamy_delights.root.entity.RoleEntity;
import com.dreamy_delights.root.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    /**
     * Finds a role entity by their name.
     *
     * @param name name of the role to retrieve; must not be {@code null}.
     * @return the {@link RoleEntity} with the specified name, or {@code null} if not found.
     */
    RoleEntity findByName(String name);
}
