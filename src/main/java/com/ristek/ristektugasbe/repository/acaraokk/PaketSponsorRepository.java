package com.ristek.ristektugasbe.repository.acaraokk;

import com.ristek.ristektugasbe.entity.acaraokk.PaketSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaketSponsorRepository extends JpaRepository<PaketSponsor, Long> {
    PaketSponsor findPaketSponsorByTipe(String tipe);
}
