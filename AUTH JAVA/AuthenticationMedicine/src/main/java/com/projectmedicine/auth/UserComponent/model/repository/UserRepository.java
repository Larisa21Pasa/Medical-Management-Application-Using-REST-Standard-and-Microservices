package com.projectmedicine.auth.UserComponent.model.repository;

import com.projectmedicine.auth.UserComponent.model.database.idm.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Integer> {
    UserTable findByUserId(Integer userId);
    Optional<UserTable> findByEmail(String email);
    UserTable findUserByEmail(String email);

}