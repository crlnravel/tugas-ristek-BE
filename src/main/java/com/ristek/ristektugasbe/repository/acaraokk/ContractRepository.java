package com.ristek.ristektugasbe.repository.acaraokk;

import com.ristek.ristektugasbe.entity.acaraokk.Contract;
import com.ristek.ristektugasbe.entity.bph.Rapat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
