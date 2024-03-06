package com.ristek.ristektugasbe.dto.dtomapper;

import com.ristek.ristektugasbe.dto.acaraokkrequest.*;
import com.ristek.ristektugasbe.dto.acaraokkresponse.*;
import com.ristek.ristektugasbe.entity.acaraokk.*;
import com.ristek.ristektugasbe.service.AcaraOkkService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcaraOkkDTOMapper {
    private final AcaraOkkService acaraOkkService;
    private final ModelMapper modelMapper;

    public AcaraOkkDTOMapper(AcaraOkkService acaraOkkService, ModelMapper modelMapper) {
        this.acaraOkkService = acaraOkkService;
        this.modelMapper = modelMapper;
    }

    public AcaraOkkDTORes convertToDto(AcaraOkk acaraOkk) {
        AcaraOkkDTORes acaraOkkDTORes = modelMapper.map(acaraOkk, AcaraOkkDTORes.class);

        if (acaraOkk.getContracts() != null) {
            List<Contract> contracts = acaraOkk.getContracts();
            List<ContractDTORes> contractDTOResList = contracts
                    .stream()
                    .map(this::convertToDto)
                    .toList();

            acaraOkkDTORes.setContracts(contractDTOResList);
        }

        return acaraOkkDTORes;
    }

    public ContractDTORes convertToDto(Contract contract) {
        ContractDTORes contractDTORes = new ContractDTORes();

        contractDTORes.setAcaraOkkId(contract.getAcaraOkk().getId());
        contractDTORes.setSponsorId(contract.getSponsor().getId());

        PaketSponsorDTORes paketSponsorDTORes = this.convertToDto(contract.getPaketSponsor());

        contractDTORes.setPaketSponsor(paketSponsorDTORes);

        return contractDTORes;
    }

    public PembicaraDTORes convertToDto(Pembicara pembicara) {
        return modelMapper.map(pembicara, PembicaraDTORes.class);
    }

    public PaketSponsorDTORes convertToDto(PaketSponsor paketSponsor) {
        return modelMapper.map(paketSponsor, PaketSponsorDTORes.class);
    }

    public SponsorDTORes convertToDto(Sponsor sponsor) {
        SponsorDTORes sponsorDTORes = modelMapper.map(sponsor, SponsorDTORes.class);

        if (sponsor.getContracts() != null) {
            List<Contract> contracts = sponsor.getContracts();
            List<ContractDTORes> contractDTOResList = contracts
                    .stream()
                    .map(this::convertToDto)
                    .toList();

            sponsorDTORes.setContracts(contractDTOResList);
        }

        return sponsorDTORes;
    }

    public AcaraOkk convertToEntity(AcaraOkkDTOReq acaraOkkDTOReq) {
        return modelMapper.map(acaraOkkDTOReq, AcaraOkk.class);
    }

    public Contract convertToEntity(ContractDTOReq contractDTOReq) {
        Contract contract = modelMapper.map(contractDTOReq, Contract.class);

        PaketSponsor paketSponsor = acaraOkkService.findPaketSponsorByType(contractDTOReq.getPaketSponsor());

        contract.setAcaraOkk(acaraOkkService.findAcaraOkkById(contractDTOReq.getAcaraOkkId()));
        contract.setSponsor(acaraOkkService.findSponsorById(contractDTOReq.getSponsorId()));
        contract.setPaketSponsor(paketSponsor);

        return contract;
    }

    public Pembicara convertToEntity(PembicaraDTOReq pembicaraDTOReq) {
        return modelMapper.map(pembicaraDTOReq, Pembicara.class);
    }

    public PaketSponsor convertToEntity(PaketSponsorDTOReq paketSponsorDTOReq) {
        return modelMapper.map(paketSponsorDTOReq, PaketSponsor.class);
    }

    public Sponsor convertToEntity(SponsorDTOReq sponsorDTOReq) {
        return modelMapper.map(sponsorDTOReq, Sponsor.class);
    }
}
