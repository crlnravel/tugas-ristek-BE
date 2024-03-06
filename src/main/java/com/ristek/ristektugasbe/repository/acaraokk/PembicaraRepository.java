package com.ristek.ristektugasbe.repository.acaraokk;

import com.ristek.ristektugasbe.entity.acaraokk.Pembicara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PembicaraRepository extends JpaRepository<Pembicara, Long> {
}
