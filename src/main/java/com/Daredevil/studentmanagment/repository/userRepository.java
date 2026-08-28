package com.Daredevil.studentmanagment.repository;

import com.Daredevil.studentmanagment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface userRepository extends JpaRepository<Users, Long> {

    boolean existsByUsername(String username);

    Optional<Users> findByUsername (String username);
}
