package com.example.admin.repository;

import com.example.admin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    <T> T findByUsername(String username, Class<T> target);

    Page<User> findAllByOrderByIdDesc(Pageable pageable);
}
