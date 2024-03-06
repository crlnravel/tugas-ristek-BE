package com.ristek.ristektugasbe.controller;

import com.ristek.ristektugasbe.dto.acaraokkrequest.AcaraOkkDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkrequest.ContractDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkrequest.PembicaraDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkrequest.SponsorDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkresponse.*;
import com.ristek.ristektugasbe.dto.dtomapper.AcaraOkkDTOMapper;
import com.ristek.ristektugasbe.entity.acaraokk.*;
import com.ristek.ristektugasbe.service.AcaraOkkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AcaraOkkController {

    private final AcaraOkkService acaraOkkService;
    private final AcaraOkkDTOMapper acaraOkkDTOMapper;

    public AcaraOkkController(AcaraOkkService acaraOkkService, AcaraOkkDTOMapper acaraOkkDTOMapper) {
        this.acaraOkkService = acaraOkkService;
        this.acaraOkkDTOMapper = acaraOkkDTOMapper;
    }


    /*
     * Controller for direct changes to acara okk
     * */

    @GetMapping("/acara")
    public ResponseEntity<List<AcaraOkkDTORes>> findAllAcaraOkk() {
        List<AcaraOkkDTORes> acaraOkkDTOResList = acaraOkkService
                .findAllAcaraOkk()
                .stream()
                .map(acaraOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(acaraOkkDTOResList, HttpStatus.OK);
    }

    @PostMapping("/acara")
    public ResponseEntity<AcaraOkkDTORes> createAcaraOkk(
            @RequestBody AcaraOkkDTOReq acaraOkkDTOReq
    ) {
        AcaraOkk acaraOkk = acaraOkkDTOMapper.convertToEntity(acaraOkkDTOReq);

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.saveAcaraOkk(acaraOkk)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/acara/{acaraId}")
    public ResponseEntity<AcaraOkkDTORes> findAcaraOkkById(
            @PathVariable("acaraId") Long acaraId
    ) {
        AcaraOkk acaraOkk = acaraOkkService.findAcaraOkkById(acaraId);

        if (acaraOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(acaraOkkDTOMapper.convertToDto(acaraOkk), HttpStatus.OK);
    }

    @PatchMapping("/acara/{acaraId}")
    public ResponseEntity<AcaraOkkDTORes> editAcaraOkk(
            @PathVariable("acaraId") Long acaraId,
            @RequestBody HashMap<String, String> body
    ) {
        AcaraOkk acaraOkk = acaraOkkService.findAcaraOkkById(acaraId);

        if (acaraOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (body.get("nama") != null) {
            acaraOkk.setNama(body.get("nama"));
        }
        if (body.get("waktu") != null) {
            try {
                acaraOkk.setWaktu(Timestamp.valueOf(body.get("waktu")));
            } catch (Exception ignored) {}
        }
        if (body.get("tempat") != null) {
            acaraOkk.setTempat(body.get("tempat"));
        }

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.saveAcaraOkk(acaraOkk)),
                HttpStatus.OK
        );
    }

    @GetMapping("acara/{acaraId}/contract")
    public ResponseEntity<List<ContractDTORes>> findContractByAcaraOkk(
            @PathVariable("acaraId") Long acaraId
    ) {
        AcaraOkk acaraOkk = acaraOkkService.findAcaraOkkById(acaraId);

        if (acaraOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                acaraOkk.getContracts()
                        .stream()
                        .map(acaraOkkDTOMapper::convertToDto)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("acara/{acaraId}/pembicara")
    public ResponseEntity<List<PembicaraDTORes>> findPembicaraByAcaraOkk(
            @PathVariable("acaraId") Long acaraId
    ) {
        AcaraOkk acaraOkk = acaraOkkService.findAcaraOkkById(acaraId);

        if (acaraOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                acaraOkk.getPembicaraList()
                        .stream()
                        .map(acaraOkkDTOMapper::convertToDto)
                        .toList(),
                HttpStatus.OK
        );
    }

    @PostMapping("/acara/{acaraId}/pembicara")
    public ResponseEntity<AcaraOkkDTORes> addPembicara (
            @PathVariable("acaraId") Long acaraId,
            @RequestBody PembicaraDTOReq pembicaraDTOReq
    ) {
        AcaraOkk acaraOkk = acaraOkkService.findAcaraOkkById(acaraId);

        if (acaraOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pembicara pembicara = acaraOkkDTOMapper.convertToEntity(pembicaraDTOReq);

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.addPembicara(acaraOkk, pembicara)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/acara/{acaraId}/pembicara/{pembicaraId}")
    public ResponseEntity<String> deletePembicara (
            @PathVariable("acaraId") Long acaraId,
            @PathVariable("pembicaraId") Long pembicaraId
    ) {
        AcaraOkk acaraOkk = acaraOkkService.findAcaraOkkById(acaraId);

        if (acaraOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pembicara pembicara = acaraOkkService.findPembicaraById(pembicaraId);

        if (pembicara == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!pembicara.getAcaraOkkList().contains(acaraOkk)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        acaraOkkService.deletePembicara(acaraOkk, pembicara);

        return new ResponseEntity<>(
                "Successfully Deleted!",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/acara/{acaraId}")
    public ResponseEntity<String> deleteAcaraOkk(
            @PathVariable("acaraId") Long acaraId
    ) {
        if (acaraOkkService.findAcaraOkkById(acaraId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        acaraOkkService.deleteAcaraOkkById(acaraId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }

    /*
     * Controller for direct changes to sponsor
     * */

    @GetMapping("/sponsor")
    public ResponseEntity<List<SponsorDTORes>> findAllSponsor() {
        List<SponsorDTORes> sponsorDTOResList = acaraOkkService
                .findAllSponsor()
                .stream()
                .map(acaraOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(sponsorDTOResList, HttpStatus.OK);
    }

    @PostMapping("/sponsor")
    public ResponseEntity<SponsorDTORes> createSponsor(
            @RequestBody SponsorDTOReq sponsorDTOReq
    ) {
        Sponsor sponsor = acaraOkkDTOMapper.convertToEntity(sponsorDTOReq);

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.saveSponsor(sponsor)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("sponsor/paket-sponsor")
    public ResponseEntity<List<PaketSponsorDTORes>> findAllPaketSponsor() {

        List<PaketSponsorDTORes> paketSponsorDTOResList = acaraOkkService
                .findAllPaketSponsor()
                .stream()
                .map(acaraOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(paketSponsorDTOResList, HttpStatus.OK);
    }

    @PatchMapping("/sponsor/paket-sponsor/{paketSponsorId}")
    public ResponseEntity<PaketSponsorDTORes> editPaketSponsor(
            @PathVariable("paketSponsorId") Long paketSponsorId,
            @RequestBody HashMap<String, String> body
    ) {
        PaketSponsor paketSponsor = acaraOkkService.findPaketSponsorById(paketSponsorId);

        if (paketSponsor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (body.get("harga") != null) {
            try {
                paketSponsor.setHarga(Double.parseDouble(body.get("harga")));
            } catch (Exception ignored) {}
        }

        if (body.get("benefit") != null) {
            paketSponsor.setBenefit(body.get("benefit"));
        }

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.savePaketSponsor(paketSponsor)),
                HttpStatus.OK
        );
    }

    @GetMapping("/sponsor/{sponsorId}")
    public ResponseEntity<SponsorDTORes> findSponsorById(
            @PathVariable("sponsorId") Long sponsorId
    ) {
        Sponsor sponsor = acaraOkkService.findSponsorById(sponsorId);

        if (sponsor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(acaraOkkDTOMapper.convertToDto(sponsor), HttpStatus.OK);
    }

    @PatchMapping("/sponsor/{sponsorId}")
    public ResponseEntity<SponsorDTORes> editSponsor(
            @PathVariable("sponsorId") Long sponsorId,
            @RequestBody HashMap<String, String> body
    ) {
        Sponsor sponsor = acaraOkkService.findSponsorById(sponsorId);

        if (sponsor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (body.get("nama") != null) {
            sponsor.setNama(body.get("nama"));
        }

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.saveSponsor(sponsor)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/sponsor/{sponsorId}")
    public ResponseEntity<String> deleteSponsor(
            @PathVariable("sponsorId") Long sponsorId
    ) {
        if (acaraOkkService.findSponsorById(sponsorId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        acaraOkkService.deleteSponsorById(sponsorId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }

    @GetMapping("sponsor/{sponsorId}/contract")
    public ResponseEntity<List<ContractDTORes>> findContractBySponsor(
            @PathVariable("sponsorId") Long sponsorId
    ) {
        Sponsor sponsor = acaraOkkService.findSponsorById(sponsorId);

        if (sponsor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                sponsor.getContracts()
                        .stream()
                        .map(acaraOkkDTOMapper::convertToDto)
                        .toList(),
                HttpStatus.OK
        );
    }

    /*
     * Controller for direct changes to contract
     * */

    @GetMapping("/contract")
    public ResponseEntity<List<ContractDTORes>> findAllContract() {
        List<ContractDTORes> contractDTOResList = acaraOkkService
                .findAllContract()
                .stream()
                .map(acaraOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(contractDTOResList, HttpStatus.OK);
    }

    @PostMapping("/contract")
    public ResponseEntity<ContractDTORes> createContract(
            @RequestBody ContractDTOReq contractDTOReq
    ) {
        Contract contract = acaraOkkDTOMapper.convertToEntity(contractDTOReq);

        if (acaraOkkService.findPaketSponsorByType(contractDTOReq.getPaketSponsor()) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.saveContract(contract.getAcaraOkk(), contract.getSponsor(), contract)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("contract/{contractId}")
    public ResponseEntity<ContractDTORes> findContractById(
            @PathVariable("contractId") Long contractId
    ) {
        Contract contract = acaraOkkService.findContractById(contractId);

        if (contract == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(acaraOkkDTOMapper.convertToDto(contract), HttpStatus.OK);
    }

    @DeleteMapping("contract/{contractId}")
    public ResponseEntity<String> deleteContractById(
            @PathVariable("contractId") Long contractId
    ) {

        if (acaraOkkService.findContractById(contractId)== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        acaraOkkService.deleteContractById(contractId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }

    /*
     * Controller for direct changes to pembicara
     * */

    @GetMapping("acara/pembicara")
    public ResponseEntity<List<PembicaraDTORes>> findAllPembicara() {
        List<PembicaraDTORes> pembicaraDTOResList = acaraOkkService
                .findAllPembicara()
                .stream()
                .map(acaraOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(pembicaraDTOResList, HttpStatus.OK);
    }

    @PostMapping("acara/pembicara")
    public ResponseEntity<PembicaraDTORes> createPembicara(
            @RequestBody PembicaraDTOReq pembicaraDTOReq
    ) {
        Pembicara pembicara = acaraOkkDTOMapper.convertToEntity(pembicaraDTOReq);

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.savePembicara(pembicara)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("acara/pembicara/{pembicaraId}")
    public ResponseEntity<PembicaraDTORes> findPembicaraById(
            @PathVariable("pembicaraId") Long pembicaraId
    ) {
        Pembicara pembicara = acaraOkkService.findPembicaraById(pembicaraId);

        if (pembicara == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(acaraOkkDTOMapper.convertToDto(pembicara), HttpStatus.OK);
    }

    @PatchMapping("acara/pembicara/{pembicaraId}")
    public ResponseEntity<PembicaraDTORes> editPembicara(
            @PathVariable("pembicaraId") Long pembicaraId,
            @RequestBody HashMap<String, String> body
    ) {
        Pembicara pembicara = acaraOkkService.findPembicaraById(pembicaraId);

        if (pembicara == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (body.get("nama") != null) {
            pembicara.setNama(body.get("nama"));
        }

        return new ResponseEntity<>(
                acaraOkkDTOMapper.convertToDto(acaraOkkService.savePembicara(pembicara)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("acara/pembicara/{pembicaraId}")
    public ResponseEntity<String> deletePembicara(
            @PathVariable("pembicaraId") Long pembicaraId
    ) {
        if (acaraOkkService.findPembicaraById(pembicaraId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        acaraOkkService.deletePembicaraById(pembicaraId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }
}
