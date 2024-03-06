package com.ristek.ristektugasbe.service.impl;

import com.ristek.ristektugasbe.entity.acaraokk.*;
import com.ristek.ristektugasbe.repository.acaraokk.*;
import com.ristek.ristektugasbe.service.AcaraOkkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcaraOkkServiceImpl implements AcaraOkkService {

    private final AcaraOkkRepository acaraOkkRepository;
    private final ContractRepository contractRepository;
    private final PaketSponsorRepository paketSponsorRepository;
    private final PembicaraRepository pembicaraRepository;
    private final SponsorRepository sponsorRepository;

    public AcaraOkkServiceImpl(AcaraOkkRepository acaraOkkRepository, ContractRepository contractRepository, PaketSponsorRepository paketSponsorRepository, PembicaraRepository pembicaraRepository, SponsorRepository sponsorRepository) {
        this.acaraOkkRepository = acaraOkkRepository;
        this.contractRepository = contractRepository;
        this.paketSponsorRepository = paketSponsorRepository;
        this.pembicaraRepository = pembicaraRepository;
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public List<AcaraOkk> findAllAcaraOkk() {
        return acaraOkkRepository.findAll();
    }

    @Override
    public AcaraOkk findAcaraOkkById(Long acaraId) {
        return acaraOkkRepository.findById(acaraId).orElse(null);
    }

    @Override
    public AcaraOkk saveAcaraOkk(AcaraOkk acaraOkk) {
        return acaraOkkRepository.save(acaraOkk);
    }

    @Override
    public AcaraOkk addPembicara(AcaraOkk acaraOkk, Pembicara pembicara) {
        List<AcaraOkk> acaraOkkList = pembicara.getAcaraOkkList();
        acaraOkkList.add(acaraOkk);
        pembicara.setAcaraOkkList(acaraOkkList);

        List<Pembicara> pembicaraList = acaraOkk.getPembicaraList();
        pembicaraList.add(pembicara);
        acaraOkk.setPembicaraList(pembicaraList);

        pembicaraRepository.save(pembicara);

        return acaraOkkRepository.save(acaraOkk);
    }

    @Override
    public void deletePembicara(AcaraOkk acaraOkk, Pembicara pembicara) {
        List<AcaraOkk> acaraOkkList = pembicara.getAcaraOkkList();
        acaraOkkList.remove(acaraOkk);
        pembicara.setAcaraOkkList(acaraOkkList);

        List<Pembicara> pembicaraList = acaraOkk.getPembicaraList();
        pembicaraList.remove(pembicara);
        acaraOkk.setPembicaraList(pembicaraList);

        pembicaraRepository.save(pembicara);

        acaraOkkRepository.save(acaraOkk);
    }

    @Override
    public void deleteAcaraOkkById(Long acaraId) {
        acaraOkkRepository.deleteById(acaraId);
    }

    @Override
    public List<Sponsor> findAllSponsor() {
        return sponsorRepository.findAll();
    }

    @Override
    public Sponsor findSponsorById(Long sponsorId) {
        return sponsorRepository.findById(sponsorId).orElse(null);
    }

    @Override
    public Sponsor saveSponsor(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    @Override
    public void deleteSponsorById(Long sponsorId) {
        sponsorRepository.deleteById(sponsorId);
    }

    @Override
    public List<Contract> findAllContract() {
        return contractRepository.findAll();
    }

    @Override
    public Contract findContractById(Long contractId) {
        return contractRepository.findById(contractId).orElse(null);
    }

    @Override
    public Contract saveContract(AcaraOkk acaraOkk, Sponsor sponsor, Contract contract) {
        contract.setAcaraOkk(acaraOkk);
        contract.setSponsor(sponsor);

        List<Contract> contractListAcara = acaraOkk.getContracts();
        contractListAcara.add(contract);
        acaraOkk.setContracts(contractListAcara);

        PaketSponsor paketSponsor = contract.getPaketSponsor();
        List<Contract> contractList = paketSponsor.getContracts();
        contractList.add(contract);
        paketSponsor.setContracts(contractList);

        List<Contract> contractListSponsor = sponsor.getContracts();
        contractListSponsor.add(contract);
        sponsor.setContracts(contractListSponsor);


        Contract savedContract = contractRepository.save(contract);
        acaraOkkRepository.save(acaraOkk);
        sponsorRepository.save(sponsor);
        paketSponsorRepository.save(paketSponsor);

        return savedContract;
    }

    @Override
    public void deleteContractById(Long contractId) {
        contractRepository.deleteById(contractId);
    }

    @Override
    public List<PaketSponsor> findAllPaketSponsor() {
        return paketSponsorRepository.findAll();
    }

    @Override
    public PaketSponsor findPaketSponsorById(Long paketSponsorId) {
        return paketSponsorRepository.findById(paketSponsorId).orElse(null);
    }

    @Override
    public PaketSponsor findPaketSponsorByType(String type) {
        return paketSponsorRepository.findPaketSponsorByTipe(type);
    }

    @Override
    public PaketSponsor savePaketSponsor(PaketSponsor paketSponsor) {
        return paketSponsorRepository.save(paketSponsor);
    }

    @Override
    public List<Pembicara> findAllPembicara() {
        return pembicaraRepository.findAll();
    }

    @Override
    public Pembicara findPembicaraById(Long pembicaraId) {
        return pembicaraRepository.findById(pembicaraId).orElse(null);
    }

    @Override
    public Pembicara savePembicara(Pembicara pembicara) {
        return pembicaraRepository.save(pembicara);
    }

    @Override
    public void deletePembicaraById(Long pembicaraId) {
        pembicaraRepository.deleteById(pembicaraId);
    }
}
