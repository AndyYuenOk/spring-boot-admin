package com.example.admin.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class ExtendedJpaRepository<T> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {
    public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}