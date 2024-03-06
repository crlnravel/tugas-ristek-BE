package com.ristek.ristektugasbe.controller;

import com.ristek.ristektugasbe.dto.acaraokkrequest.AcaraOkkDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkrequest.ContractDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkrequest.PembicaraDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkrequest.SponsorDTOReq;
import com.ristek.ristektugasbe.dto.acaraokkresponse.AcaraOkkDTORes;
import com.ristek.ristektugasbe.dto.acaraokkresponse.ContractDTORes;
import com.ristek.ristektugasbe.dto.acaraokkresponse.PembicaraDTORes;
import com.ristek.ristektugasbe.dto.acaraokkresponse.SponsorDTORes;
import com.ristek.ristektugasbe.dto.dtomapper.AcaraOkkDTOMapper;
import com.ristek.ristektugasbe.entity.acaraokk.AcaraOkk;
import com.ristek.ristektugasbe.entity.acaraokk.Contract;
import com.ristek.ristektugasbe.entity.acaraokk.Pembicara;
import com.ristek.ristektugasbe.entity.acaraokk.Sponsor;
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
