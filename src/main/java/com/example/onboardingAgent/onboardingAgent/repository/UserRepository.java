package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    long countBy(); // total users

    long countByDocumentRequiredTrueAndVerificationRequiredFalse();

    long countByDocumentRequiredFalseAndVerificationRequiredTrue();

    long countByManagerIsNull();

    @Query("""
    SELECT MONTH(u.dateOfJoining), COUNT(u)
    FROM UserEntity u
    WHERE YEAR(u.dateOfJoining) = :year
    GROUP BY MONTH(u.dateOfJoining)
    ORDER BY MONTH(u.dateOfJoining)
""")
    List<Object[]> countHiresByMonth(@Param("year") int year);

}
