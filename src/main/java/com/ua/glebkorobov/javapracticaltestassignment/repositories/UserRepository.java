package com.ua.glebkorobov.javapracticaltestassignment.repositories;

import com.ua.glebkorobov.javapracticaltestassignment.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByBirthdayBetween(LocalDate fromDate, LocalDate toDate);
}
