package com.example.admin.repository;

import com.example.admin.entity.User;
import com.example.admin.dto.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    <T> T findByUsername(String username, Class<T> target);

    Page<UserProjection> findAllByOrderByIdDesc(Pageable pageable);
}
