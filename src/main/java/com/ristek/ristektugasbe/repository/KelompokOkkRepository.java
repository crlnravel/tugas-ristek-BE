package com.ristek.ristektugasbe.repository;

import com.ristek.ristektugasbe.model.KelompokOkk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KelompokOkkRepository extends JpaRepository<KelompokOkk, Long> {
}
