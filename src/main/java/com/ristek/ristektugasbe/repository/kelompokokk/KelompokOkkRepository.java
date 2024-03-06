package com.ristek.ristektugasbe.repository.kelompokokk;

import com.ristek.ristektugasbe.entity.kelompokokk.KelompokOkk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KelompokOkkRepository extends JpaRepository<KelompokOkk, Long> {
}
