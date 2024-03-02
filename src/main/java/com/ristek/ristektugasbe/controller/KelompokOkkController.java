package com.ristek.ristektugasbe.controller;

import com.ristek.ristektugasbe.dto.KelompokOkkDTO;
import com.ristek.ristektugasbe.dto.MentoringSessionDTO;
import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.Mentee;
import com.ristek.ristektugasbe.model.Mentor;
import com.ristek.ristektugasbe.model.MentoringSession;
import com.ristek.ristektugasbe.service.KelompokOkkService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kelompok-okk")
@CrossOrigin(originPatterns = "*")
public class KelompokOkkController {

    private final KelompokOkkService kelompokOKKService;
    private final ModelMapper modelMapper;

    public KelompokOkkController(KelompokOkkService kelompokOKKService, ModelMapper modelMapper) {
        this.kelompokOKKService = kelompokOKKService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<KelompokOkkDTO>> findAllKelompokOkk() {
        List<KelompokOkk> kelompokOkkList = kelompokOKKService.findAllKelompokOkk();

        return new ResponseEntity<>(kelompokOkkList.stream()
                .map(kelompokOkk -> modelMapper.map(kelompokOkk, KelompokOkkDTO.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KelompokOkkDTO> findKelompokOkkById(@PathVariable("id") Long id) {
        KelompokOkk kelompokOKK = kelompokOKKService.findKelompokOkkById(id);

        if (kelompokOKK == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        KelompokOkkDTO kelompokOKKDTO = modelMapper.map(kelompokOKK, KelompokOkkDTO.class);

        return new ResponseEntity<>(kelompokOKKDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<KelompokOkkDTO> saveKelompokOkk(@RequestBody KelompokOkkDTO kelompokOkkDTO) {
        KelompokOkk kelompokOkk = modelMapper.map(kelompokOkkDTO, KelompokOkk.class);
        Mentor mentor = modelMapper.map(kelompokOkkDTO.getMentor(), Mentor.class);
        List<Mentee> mentees = kelompokOkkDTO.getMentees()
                .stream()
                .map(menteeDTO -> modelMapper.map(menteeDTO, Mentee.class))
                .toList();

        mentor.setKelompokOkk(kelompokOkk);
        mentees.forEach(mentee -> mentee.setKelompokOkk(kelompokOkk));

        kelompokOkk.setMentor(mentor);
        kelompokOkk.setMentees(mentees);

        return new ResponseEntity<>(
                modelMapper.map(kelompokOKKService.saveKelompokOkk(kelompokOkk), KelompokOkkDTO.class),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<KelompokOkkDTO> updateKelompokOkk(@RequestBody KelompokOkkDTO kelompokOkkDTO) {
        KelompokOkk kelompokOkk = modelMapper.map(kelompokOkkDTO, KelompokOkk.class);
        Mentor mentor = modelMapper.map(kelompokOkkDTO.getMentor(), Mentor.class);
        List<Mentee> mentees = kelompokOkkDTO.getMentees()
                .stream()
                .map(menteeDTO -> modelMapper.map(menteeDTO, Mentee.class))
                .toList();

        mentor.setKelompokOkk(kelompokOkk);
        mentees.forEach(mentee -> mentee.setKelompokOkk(kelompokOkk));

        kelompokOkk.setMentor(mentor);
        kelompokOkk.setMentees(mentees);

        return new ResponseEntity<>(
                modelMapper.map(kelompokOKKService.updateKelompokOkk(kelompokOkk), KelompokOkkDTO.class),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKelompokOkk(@PathVariable("id") Long id) {
        KelompokOkk kelompokOkk = kelompokOKKService.findKelompokOkkById(id);

        if (kelompokOkk == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        kelompokOKKService.deleteKelompokOkkById(id);

        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }

    @PostMapping("/{id}/add-mentoring")
    public ResponseEntity<KelompokOkkDTO> addMentoringSession(@PathVariable("id") Long id, @RequestBody MentoringSessionDTO mentoringSessionDTO) {

        if (!mentoringSessionDTO.getKelompokOkkId().equals(id)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        KelompokOkk kelompokOkk = kelompokOKKService.findKelompokOkkById(id);

        MentoringSession mentoringSession = modelMapper.map(mentoringSessionDTO, MentoringSession.class);
        mentoringSession.setKelompokOkk(kelompokOkk);

        List<Mentee> mentees = mentoringSessionDTO.getMenteeIds()
                .stream()
                .map(menteeId -> kelompokOKKService.findMenteeDetailById(id))
                .toList();
        mentoringSession.setMentees(mentees);

        mentees.forEach(mentee -> {
            List<MentoringSession> mentoringSessions = mentee.getMentoringSessions();
            mentoringSessions.add(mentoringSession);
            mentee.setMentoringSessions(mentoringSessions);
        });

        KelompokOkkDTO kelompokOkkDTO = modelMapper.map(kelompokOKKService.addMentoringSession(id, mentoringSession), KelompokOkkDTO.class);

        kelompokOkkDTO.getMentoringSessions().forEach(mentoring -> mentoring.setMentor(kelompokOkkDTO.getMentor()));

        return new ResponseEntity<>(
                kelompokOkkDTO,
                HttpStatus.OK
        );
    }
}

