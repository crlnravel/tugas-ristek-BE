package com.ristek.ristektugasbe.repository.bph;

import com.ristek.ristektugasbe.entity.bph.Rapat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapatRepository extends JpaRepository<Rapat, Long> {
}
