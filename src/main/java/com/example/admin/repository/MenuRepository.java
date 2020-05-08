package com.example.admin.repository;

import com.example.admin.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Set<Menu> findByIsMenu(Boolean isMenu);

    Page<Menu> findAllByOrderByIdDesc(Pageable pageable);
}
