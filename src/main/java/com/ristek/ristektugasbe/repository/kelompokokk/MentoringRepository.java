package com.ristek.ristektugasbe.repository.kelompokokk;

import com.ristek.ristektugasbe.entity.kelompokokk.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentoringRepository extends JpaRepository<Mentoring, Long> {
}

