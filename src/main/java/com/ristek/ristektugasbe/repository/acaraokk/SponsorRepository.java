package com.ristek.ristektugasbe.repository.acaraokk;

import com.ristek.ristektugasbe.entity.acaraokk.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}
