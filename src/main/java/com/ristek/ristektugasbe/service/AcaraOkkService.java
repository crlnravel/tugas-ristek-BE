package com.ristek.ristektugasbe.service;

import com.ristek.ristektugasbe.entity.acaraokk.*;

import java.util.List;

public interface AcaraOkkService {
    List<AcaraOkk> findAllAcaraOkk();
    AcaraOkk findAcaraOkkById(Long acaraId);
    AcaraOkk saveAcaraOkk(AcaraOkk acaraOkk);
    AcaraOkk addPembicara(AcaraOkk acaraOkk, Pembicara pembicara);
    void deletePembicara(AcaraOkk acaraOkk, Pembicara pembicara);
    void deleteAcaraOkkById(Long acaraId);
    List<Sponsor> findAllSponsor();
    Sponsor findSponsorById(Long sponsorId);
    Sponsor saveSponsor(Sponsor sponsor);
    void deleteSponsorById(Long sponsorId);
    List<Contract> findAllContract();
    Contract findContractById(Long contractId);
    Contract saveContract(AcaraOkk acaraOkk, Sponsor sponsor, Contract contract);
    void deleteContractById(Long contractId);
    List<PaketSponsor> findAllPaketSponsor();
    PaketSponsor findPaketSponsorById(Long paketSponsorId);
    PaketSponsor findPaketSponsorByType(String type);
    PaketSponsor savePaketSponsor(PaketSponsor paketSponsor);
    List<Pembicara> findAllPembicara();
    Pembicara findPembicaraById(Long pembicaraId);
    Pembicara savePembicara(Pembicara pembicara);
    void deletePembicaraById(Long pembicaraId);
}
