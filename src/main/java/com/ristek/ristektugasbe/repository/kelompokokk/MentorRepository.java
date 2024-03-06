package com.ristek.ristektugasbe.repository.kelompokokk;

import com.ristek.ristektugasbe.entity.kelompokokk.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
