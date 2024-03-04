package com.ristek.ristektugasbe.repository;

import com.ristek.ristektugasbe.model.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentoringRepository extends JpaRepository<Mentoring, Long> {
}

