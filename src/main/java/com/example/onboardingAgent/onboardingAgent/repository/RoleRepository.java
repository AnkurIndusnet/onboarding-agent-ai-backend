package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query("SELECT r.roleName FROM RoleEntity r WHERE r.id = :id")
    String findRoleNameById(@Param("id") Integer id);
}