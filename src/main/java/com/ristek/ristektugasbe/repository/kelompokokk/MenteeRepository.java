package com.ristek.ristektugasbe.repository.kelompokokk;

import com.ristek.ristektugasbe.entity.kelompokokk.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {
}
