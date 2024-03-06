package com.ristek.ristektugasbe.repository.bph;

import com.ristek.ristektugasbe.entity.bph.Bph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BphRepository extends JpaRepository<Bph, Long> {
}