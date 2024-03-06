package com.ristek.ristektugasbe.controller;

import com.ristek.ristektugasbe.dto.dtomapper.KelompokOkkDTOMapper;
import com.ristek.ristektugasbe.dto.kelompokokkrequest.KelompokOkkDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkrequest.MenteeDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkrequest.MentorDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkrequest.MentoringDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.KelompokOkkDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MenteeDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MentorDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MentoringDTORes;
import com.ristek.ristektugasbe.entity.kelompokokk.KelompokOkk;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentee;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentor;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentoring;
import com.ristek.ristektugasbe.service.KelompokOkkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/kelompok-okk")
public class KelompokOkkController {

    private final KelompokOkkService kelompokOkkService;
    private final KelompokOkkDTOMapper kelompokOkkDTOMapper;

    public KelompokOkkController(KelompokOkkService kelompokOkkService, KelompokOkkDTOMapper kelompokOkkDTOMapper) {
        this.kelompokOkkService = kelompokOkkService;
        this.kelompokOkkDTOMapper = kelompokOkkDTOMapper;
    }

    /*
    * Controller for direct changes to kelompok okk
    * */

    @GetMapping
    public ResponseEntity<List<KelompokOkkDTORes>> findAllKelompokOkk() {
        List<KelompokOkkDTORes> kelompokOkkDTOResList = kelompokOkkService
                .findAllKelompokOkk()
                .stream()
                .map(kelompokOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(kelompokOkkDTOResList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<KelompokOkkDTORes> createKelompokOkk(
            @RequestBody KelompokOkkDTOReq kelompokOkkDTOReq
    ) {
        KelompokOkk kelompokOkk = kelompokOkkDTOMapper.convertToEntity(kelompokOkkDTOReq);

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(kelompokOkkService.saveKelompokOkk(kelompokOkk)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{kelompokId}")
    public ResponseEntity<KelompokOkkDTORes> findKelompokOkkById(
            @PathVariable("kelompokId") Long kelompokId
    ) {
        KelompokOkk kelompokOkk = kelompokOkkService.findKelompokOkkById(kelompokId);

        if (kelompokOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(kelompokOkkDTOMapper.convertToDto(kelompokOkk), HttpStatus.OK);
    }

    @PatchMapping("/{kelompokId}")
    public ResponseEntity<KelompokOkkDTORes> editNomorKelompokOkk(
            @PathVariable("kelompokId") Long kelompokId,
            @RequestBody HashMap<String, String> body
            ) {
        KelompokOkk kelompokOkk = kelompokOkkService.findKelompokOkkById(kelompokId);

        if (kelompokOkk == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        kelompokOkk.setNomor(body.get("nomor"));

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(kelompokOkkService.saveKelompokOkk(kelompokOkk)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{kelompokId}")
    public ResponseEntity<String> deleteKelompokOkk(
            @PathVariable("kelompokId") Long kelompokId
    ) {
        if (kelompokOkkService.findKelompokOkkById(kelompokId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        kelompokOkkService.deleteKelompokOkkById(kelompokId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }

    /*
    * Controller for direct transaction to
    * */

    @GetMapping("/{kelompokId}/mentoring")
    public ResponseEntity<List<MentoringDTORes>> findAllMentoring(
            @PathVariable("kelompokId") Long kelompokId
    ) {
        List<Mentoring> mentoringList = kelompokOkkService.findAllMentoring(kelompokId);

        if (mentoringList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<MentoringDTORes> mentoringDTOResList = mentoringList
                .stream()
                .map(kelompokOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(mentoringDTOResList, HttpStatus.OK);
    }

    @PostMapping("/{kelompokId}/mentoring")
    public ResponseEntity<MentoringDTORes> createMentoring(
            @PathVariable("kelompokId") Long kelompokId,
            @RequestBody MentoringDTOReq mentoringDTOReq
    ) {
        Mentoring mentoring = kelompokOkkService.saveMentoring(
                kelompokId,
                kelompokOkkDTOMapper.convertToEntity(mentoringDTOReq)
        );

        if (mentoring == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Mentee mentee: mentoring.getMentees()) {
            if (!Objects.equals(mentee.getKelompokOkk().getId(), kelompokId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(mentoring),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{kelompokId}/mentoring/{mentoringId}")
    public ResponseEntity<MentoringDTORes> findMentoringById(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("mentoringId") Long mentoringId
    ) {
        if (kelompokOkkService.findKelompokOkkById(kelompokId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Mentoring mentoring = kelompokOkkService.findMentoringById(mentoringId);

        if (mentoring == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(mentoring.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(mentoring),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{kelompokId}/mentoring/{mentoringId}")
    public ResponseEntity<MentoringDTORes> editMentoringInfo(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("mentoringId") Long mentoringId,
            @RequestBody HashMap<String, String> body
    ) {
        if (kelompokOkkService.findKelompokOkkById(kelompokId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Mentoring mentoring = kelompokOkkService.findMentoringById(mentoringId);

        if (mentoring == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(mentoring.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        mentoring.setTempat(body.get("tempat"));
        mentoring.setWaktu(Timestamp.valueOf(body.get("waktu")));
        mentoring.setMateri(body.get("materi"));

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(kelompokOkkService.saveMentoring(kelompokId, mentoring)),
                HttpStatus.OK
        );
    }

    @PostMapping("/{kelompokId}/mentoring/{mentoringId}/peserta")
    public ResponseEntity<MentoringDTORes> addPesertaMentoring(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("mentoringId") Long mentoringId,
            @RequestBody HashMap<String, Long> body
    ) {
        Mentoring mentoring = kelompokOkkService.findMentoringById(mentoringId);

        if (mentoring == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(mentoring.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Mentee mentee = kelompokOkkService.findMenteeById(body.get("menteeId"));

        if (mentee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(mentee.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(
                        kelompokOkkService.addPesertaMentoring(kelompokId, mentoring, mentee)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{kelompokId}/mentoring/{mentoringId}/peserta")
    public ResponseEntity<String> deletePesertaMentoring(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("mentoringId") Long mentoringId,
            @RequestBody HashMap<String, Long> body
    ) {
        Mentoring mentoring = kelompokOkkService.findMentoringById(mentoringId);

        if (mentoring == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(mentoring.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Mentee mentee = kelompokOkkService.findMenteeById(body.get("menteeId"));

        if (mentee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(mentee.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        kelompokOkkService.deletePesertaMentoring(kelompokId, mentoring, mentee);

        return new ResponseEntity<>(
                "Successfully Deleted!",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{kelompokId}/mentoring/{mentoringId}")
    public ResponseEntity<String> deleteMentoringById(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("mentoringId") Long mentoringId
    ) {
        if (kelompokOkkService.findMentoringById(mentoringId) == null ||
                kelompokOkkService.findKelompokOkkById(kelompokId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        kelompokOkkService.deleteMentoringById(mentoringId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }

    /*
    * Controller for direct transaction to mentor
    * */

    @GetMapping("/{kelompokId}/mentor")
    public ResponseEntity<MentorDTORes> findMentor(
            @PathVariable("kelompokId") Long kelompokId
    ) {
        Mentor mentor = kelompokOkkService.findMentor(kelompokId);

        if (mentor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(mentor),
                HttpStatus.OK
        );
    }

    @PutMapping("/{kelompokId}/mentor")
    public ResponseEntity<MentorDTORes> ubahMentor(
            @PathVariable("kelompokId") Long id,
            @RequestBody MentorDTOReq mentorDTOReq
    ) {
        if (kelompokOkkService.findKelompokOkkById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Mentor mentor = kelompokOkkDTOMapper.convertToEntity(mentorDTOReq);

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(kelompokOkkService.updateMentor(id, mentor)),
                HttpStatus.OK
        );
    }

    /*
     * Controller for direct transaction to mentee
     * */

    @GetMapping("/{kelompokId}/mentee")
    public ResponseEntity<List<MenteeDTORes>> findAllMentees(
            @PathVariable("kelompokId") Long kelompokId
    ) {
        List<Mentee> menteeList = kelompokOkkService.findAllMentee(kelompokId);

        if (menteeList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<MenteeDTORes> menteeDTOResList = menteeList
                .stream()
                .map(kelompokOkkDTOMapper::convertToDto)
                .toList();

        return new ResponseEntity<>(menteeDTOResList, HttpStatus.OK);
    }

    @PostMapping("/{kelompokId}/mentee")
    public ResponseEntity<MenteeDTORes> addNewMentee(
            @PathVariable("kelompokId") Long kelompokId,
            @RequestBody MenteeDTOReq menteeDTOReq
    ) {
        Mentee mentee = kelompokOkkService.saveMentee(kelompokId, kelompokOkkDTOMapper.convertToEntity(menteeDTOReq));

        if (mentee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(mentee),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{kelompokId}/mentee/{menteeId}")
    public ResponseEntity<MenteeDTORes> findMenteeById(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("menteeId") Long menteeId
            ) {
        if (kelompokOkkService.findKelompokOkkById(kelompokId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Mentee mentee = kelompokOkkService.findMenteeById(menteeId);

        if (mentee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(mentee.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(mentee),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{kelompokId}/mentee/{menteeId}")
    public ResponseEntity<MenteeDTORes> editMenteeInfo(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("menteeId") Long menteeId,
            @RequestBody HashMap<String, String> body
    ) {
        if (kelompokOkkService.findKelompokOkkById(kelompokId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Mentee mentee = kelompokOkkService.findMenteeById(menteeId);

        if (mentee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(mentee.getKelompokOkk().getId(), kelompokId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        mentee.setNama(body.get("nama"));
        mentee.setJalurMasuk(body.get("jalurMasuk"));

        return new ResponseEntity<>(
                kelompokOkkDTOMapper.convertToDto(kelompokOkkService.saveMentee(kelompokId, mentee)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{kelompokId}/mentee/{menteeId}")
    public ResponseEntity<String> deleteMenteeById(
            @PathVariable("kelompokId") Long kelompokId,
            @PathVariable("menteeId") Long menteeId
    ) {
        if (kelompokOkkService.findMenteeById(menteeId) == null ||
                kelompokOkkService.findKelompokOkkById(kelompokId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        kelompokOkkService.deleteMenteeById(menteeId);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }
}