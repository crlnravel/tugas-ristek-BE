package com.ristek.ristektugasbe.repository.acaraokk;

import com.ristek.ristektugasbe.entity.acaraokk.AcaraOkk;
import com.ristek.ristektugasbe.entity.bph.Bph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcaraOkkRepository extends JpaRepository<AcaraOkk, Long> {
}