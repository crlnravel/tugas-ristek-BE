package com.ristek.ristektugasbe.controller;

import com.ristek.ristektugasbe.dto.bphrequest.RapatDTOReq;
import com.ristek.ristektugasbe.dto.bphrequest.StaffDTOReq;
import com.ristek.ristektugasbe.dto.bphresponse.BphDTORes;
import com.ristek.ristektugasbe.dto.bphresponse.RapatDTORes;
import com.ristek.ristektugasbe.dto.bphresponse.StaffDTORes;
import com.ristek.ristektugasbe.dto.dtomapper.BphDTOMapper;
import com.ristek.ristektugasbe.entity.bph.Bph;
import com.ristek.ristektugasbe.entity.bph.Rapat;
import com.ristek.ristektugasbe.entity.bph.Staff;
import com.ristek.ristektugasbe.service.BphService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/bph")
public class BphController {

    private final BphService bphService;
    private final BphDTOMapper bphDTOMapper;

    public BphController(BphService bphService, BphDTOMapper bphDTOMapper) {
        this.bphService = bphService;
        this.bphDTOMapper = bphDTOMapper;
    }

    /*
     * Controller for direct changes to kelompok okk
     * */

    @GetMapping
    public ResponseEntity<List<BphDTORes>> findAllBph() {
        List<BphDTORes> bphDTOResList = bphService
                .findAllBph()
                .stream()
                .map(bphDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(bphDTOResList, HttpStatus.OK);
    }

    @GetMapping("/{bphId}")
    public ResponseEntity<BphDTORes> findBphById(
            @PathVariable("bphId") Long bphId
    ) {
        Bph bph = bphService.findBphById(bphId);

        if (bph == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(bphDTOMapper.convertToDto(bph), HttpStatus.OK);
    }

    /*
     * Controller for direct transaction to rapat
     * */

    @GetMapping("/{bphId}/rapat")
    public ResponseEntity<List<RapatDTORes>> findAllRapat(
            @PathVariable("bphId") Long bphId
    ) {
        List<Rapat> rapatList = bphService.findAllRapat(bphId);

        if (rapatList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<RapatDTORes> rapatDTOResList = rapatList
                .stream()
                .map(bphDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(rapatDTOResList, HttpStatus.OK);
    }

    @PostMapping("/{bphId}/rapat")
    public ResponseEntity<RapatDTORes> createRapat(
            @PathVariable("bphId") Long bphId,
            @RequestBody RapatDTOReq rapatDTOReq
    ) {
        Rapat rapat = bphService.saveRapat(
                bphId,
                bphDTOMapper.convertToEntity(rapatDTOReq)
        );

        if (rapat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Staff staff: rapat.getStaffList()) {
            if (!Objects.equals(staff.getBph().getId(), bphId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(
                bphDTOMapper.convertToDto(rapat),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{bphId}/rapat/{rapatId}")
    public ResponseEntity<RapatDTORes> findRapatById(
            @PathVariable("bphId") Long bphId,
            @PathVariable("rapatId") Long rapatId
    ) {
        if (bphService.findBphById(bphId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Rapat rapat = bphService.findRapatById(rapatId);

        if (rapat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(rapat.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                bphDTOMapper.convertToDto(rapat),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{bphId}/rapat/{rapatId}")
    public ResponseEntity<RapatDTORes> editRapatInfo(
            @PathVariable("bphId") Long bphId,
            @PathVariable("rapatId") Long rapatId,
            @RequestBody HashMap<String, String> body
    ) {
        if (bphService.findBphById(bphId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Rapat rapat = bphService.findRapatById(rapatId);

        if (rapat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(rapat.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (body.get("kesimpulan") != null) {
            rapat.setKesimpulan(body.get("kesimpulan"));
        }
        if (body.get("waktu") != null) {
            try {
                rapat.setWaktu(Timestamp.valueOf(body.get("waktu")));
            } catch (Exception ignored) {}
        }
        if (body.get("tempat") != null) {
            rapat.setTempat(body.get("tempat"));
        }

        return new ResponseEntity<>(
                bphDTOMapper.convertToDto(bphService.saveRapat(bphId, rapat)),
                HttpStatus.OK
        );
    }

    @PostMapping("/{bphId}/rapat/{rapatId}/peserta")
    public ResponseEntity<RapatDTORes> addPesertaRapat(
            @PathVariable("bphId") Long bphId,
            @PathVariable("rapatId") Long rapatId,
            @RequestBody HashMap<String, Long> body
    ) {
        Rapat rapat = bphService.findRapatById(rapatId);

        if (rapat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(rapat.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Staff staff = bphService.findStaffById(body.get("staffId"));

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(staff.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                bphDTOMapper.convertToDto(
                        bphService.addPesertaRapat(bphId, rapat, staff)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{bphId}/rapat/{rapatId}/peserta/{staffId}")
    public ResponseEntity<String> deletePesertaRapat(
            @PathVariable("bphId") Long bphId,
            @PathVariable("rapatId") Long rapatId,
            @PathVariable("staffId") Long staffId
    ) {
        Rapat rapat = bphService.findRapatById(rapatId);

        if (rapat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(rapat.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Staff staff = bphService.findStaffById(staffId);

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(staff.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        bphService.deletePesertaRapat(bphId, rapat, staff);

        return new ResponseEntity<>(
                "Successfully Deleted!",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{bphId}/rapat/{rapatId}")
    public ResponseEntity<String> deleteRapatById(
            @PathVariable("bphId") Long bphId,
            @PathVariable("rapatId") Long rapatId
    ) {
        if (bphService.findRapatById(rapatId) == null ||
                bphService.findBphById(bphId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bphService.deleteRapatById(rapatId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }


    /*
     * Controller for direct transaction to staff
     * */

    @GetMapping("/{bphId}/staff")
    public ResponseEntity<List<StaffDTORes>> findAllStaffs(
            @PathVariable("bphId") Long bphId
    ) {
        List<Staff> staffList = bphService.findAllStaff(bphId);

        if (staffList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<StaffDTORes> staffDTOResList = staffList
                .stream()
                .map(bphDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(staffDTOResList, HttpStatus.OK);
    }

    @PostMapping("/{bphId}/staff")
    public ResponseEntity<StaffDTORes> addNewStaff(
            @PathVariable("bphId") Long bphId,
            @RequestBody StaffDTOReq staffDTOReq
    ) {
        Staff staff = bphService.saveStaff(bphId, bphDTOMapper.convertToEntity(staffDTOReq));

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                bphDTOMapper.convertToDto(staff),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{bphId}/staff/{staffId}")
    public ResponseEntity<StaffDTORes> findStaffById(
            @PathVariable("bphId") Long bphId,
            @PathVariable("staffId") Long staffId
    ) {
        if (bphService.findBphById(bphId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Staff staff = bphService.findStaffById(staffId);

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(staff.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                bphDTOMapper.convertToDto(staff),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{bphId}/staff/{staffId}")
    public ResponseEntity<StaffDTORes> editStaffInfo(
            @PathVariable("bphId") Long bphId,
            @PathVariable("staffId") Long staffId,
            @RequestBody HashMap<String, String> body
    ) {
        if (bphService.findBphById(bphId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Staff staff = bphService.findStaffById(staffId);

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(staff.getBph().getId(), bphId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (body.get("nama") != null) {
            staff.setNama(body.get("nama"));
        }
        if (body.get("fakultas") != null) {
            staff.setFakultas(body.get("fakultas"));
        }
        if (body.get("jurusan") != null) {
            staff.setJurusan(body.get("jurusan"));
        }

        try {
            staff.setAngkatan(Integer.parseInt(body.get("jalurMasuk")));
        } catch (Exception ignored) {}

        return new ResponseEntity<>(
                bphDTOMapper.convertToDto(bphService.saveStaff(bphId, staff)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{bphId}/staff/{staffId}")
    public ResponseEntity<String> deleteStaffById(
            @PathVariable("bphId") Long bphId,
            @PathVariable("staffId") Long staffId
    ) {
        if (bphService.findStaffById(staffId) == null ||
                bphService.findBphById(bphId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bphService.deleteStaffById(staffId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }
}