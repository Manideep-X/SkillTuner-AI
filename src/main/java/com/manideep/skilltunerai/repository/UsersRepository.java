package com.manideep.skilltunerai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manideep.skilltunerai.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // Optional is used for explicitly handling null return case (recommended)
    // Internal query: select * from table_user where email = ?1;
    Optional<Users> findByEmail(String email);

}
