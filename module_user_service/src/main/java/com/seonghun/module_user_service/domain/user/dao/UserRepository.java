package com.seonghun.module_user_service.domain.user.dao;

import com.seonghun.module_user_service.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
    Users findByUsername(String username);

}