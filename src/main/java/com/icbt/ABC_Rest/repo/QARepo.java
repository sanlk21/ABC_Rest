package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.QA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QARepo extends JpaRepository<QA, Long> {
    List<QA> findByUserEmail(String email);
}