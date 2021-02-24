package com.gabrielduarte.luckyexperiment.repository;

import com.gabrielduarte.luckyexperiment.domain.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
