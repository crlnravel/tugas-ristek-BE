package com.ristek.ristektugasbe.repository;

import com.ristek.ristektugasbe.model.MentoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentoringSessionRepository extends JpaRepository<MentoringSession, Long> {
}

