package com.ristek.ristektugasbe.controller;

import com.ristek.ristektugasbe.dto.KelompokOkkDTO;
import com.ristek.ristektugasbe.dto.MenteeDTO;
import com.ristek.ristektugasbe.dto.MentoringSessionDTO;
import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.Mentee;
import com.ristek.ristektugasbe.model.Mentor;
import com.ristek.ristektugasbe.model.MentoringSession;
import com.ristek.ristektugasbe.service.KelompokOkkService;
import com.ristek.ristektugasbe.service.MentoringSessionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/kelompok-okk")
@CrossOrigin(originPatterns = "*")
public class KelompokOkkController {

    private final KelompokOkkService kelompokOKKService;
    private final MentoringSessionService mentoringSessionService;
    private final ModelMapper modelMapper;

    public KelompokOkkController(KelompokOkkService kelompokOKKService, MentoringSessionService mentoringSessionService, ModelMapper modelMapper) {
        this.kelompokOKKService = kelompokOKKService;
        this.mentoringSessionService = mentoringSessionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<KelompokOkkDTO>> findAllKelompokOkk() {
        List<KelompokOkk> kelompokOkkList = kelompokOKKService.findAllKelompokOkk();

        return new ResponseEntity<>(kelompokOkkList.stream()
                .map(this::kelompokOkkToDTO)
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KelompokOkkDTO> findKelompokOkkById(@PathVariable("id") Long id) {
        KelompokOkk kelompokOKK = kelompokOKKService.findKelompokOkkById(id);

        if (kelompokOKK == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        KelompokOkkDTO kelompokOKKDTO = this.kelompokOkkToDTO(kelompokOKK);

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
                this.kelompokOkkToDTO(kelompokOKKService.saveKelompokOkk(kelompokOkk)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<KelompokOkkDTO> updateKelompokOkk(@PathVariable("id") Long id, @RequestBody KelompokOkkDTO kelompokOkkDTO) {
        KelompokOkk kelompokOkk = kelompokOKKService.findKelompokOkkById(id);

        if (kelompokOkk == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else if (!id.equals(kelompokOkk.getId())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        kelompokOkk = modelMapper.map(kelompokOkkDTO, KelompokOkk.class);
        Mentor mentor = modelMapper.map(kelompokOkkDTO.getMentor(), Mentor.class);
        List<Mentee> mentees = kelompokOkkDTO.getMentees()
                .stream()
                .map(menteeDTO -> modelMapper.map(menteeDTO, Mentee.class))
                .toList();

        mentor.setKelompokOkk(kelompokOkk);
        KelompokOkk finalKelompokOkk = kelompokOkk;
        mentees.forEach(mentee -> mentee.setKelompokOkk(finalKelompokOkk));

        kelompokOkk.setMentor(mentor);
        kelompokOkk.setMentees(mentees);

        return new ResponseEntity<>(
                this.kelompokOkkToDTO(kelompokOKKService.updateKelompokOkk(kelompokOkk)),
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

    @PostMapping("/{id}/add-mentee")
    public ResponseEntity<KelompokOkkDTO> addMentee(@PathVariable("id") Long id, @RequestBody MenteeDTO menteeDTO) {
        KelompokOkk kelompokOkk = kelompokOKKService.findKelompokOkkById(id);

        if (kelompokOkk == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Mentee mentee = modelMapper.map(menteeDTO, Mentee.class);

        return new ResponseEntity<>(
                this.kelompokOkkToDTO(kelompokOKKService.addMentee(id, mentee)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}/mentoring")
    public ResponseEntity<List<MentoringSessionDTO>> findAllMentoringSessionByKelompokOkk(@PathVariable("id") Long id) {
        KelompokOkk kelompokOkk = kelompokOKKService.findKelompokOkkById(id);

        if (kelompokOkk == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<MentoringSession> mentoringSessions= mentoringSessionService.findAllMentoringSessionByKelompokOkkId(id);

        List<MentoringSessionDTO> mentoringSessionDTOS = mentoringSessions.stream()
                .map(mentoringSession -> modelMapper.map(mentoringSession, MentoringSessionDTO.class))
                .toList();

        return new ResponseEntity<>(mentoringSessionDTOS, HttpStatus.OK);
    }

    @GetMapping("/mentoring/{mentoring_id}")
    public ResponseEntity<MentoringSessionDTO> findMentoringSessionById(@PathVariable("mentoring_id") Long mentoring_id) {
        MentoringSession mentoringSession = mentoringSessionService.findMentoringSessionById(mentoring_id);

        if (mentoringSession == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(modelMapper.map(mentoringSession, MentoringSessionDTO.class), HttpStatus.OK);
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
            if (mentee != null) {
                List<MentoringSession> mentoringSessions = mentee.getMentoringSessions();
                mentoringSessions.add(mentoringSession);
                mentee.setMentoringSessions(mentoringSessions);
            }
        });

        KelompokOkkDTO kelompokOkkDTO = kelompokOkkToDTO(kelompokOKKService.addMentoringSession(id, mentoringSession));

        return new ResponseEntity<>(
                kelompokOkkDTO,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/mentoring/{mentoring-id}")
    public ResponseEntity<String> deleteMentoringSession(@PathVariable("mentoring_id") Long mentoring_id) {
        MentoringSession mentoringSession = mentoringSessionService.findMentoringSessionById(mentoring_id);

        if (mentoringSession == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        mentoringSessionService.deleteMentoringSessionById(mentoring_id);

        return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);
    }

    private KelompokOkkDTO kelompokOkkToDTO(KelompokOkk kelompokOkk) {
        KelompokOkkDTO kelompokOkkDTO = modelMapper.map(kelompokOkk, KelompokOkkDTO.class);

        kelompokOkkDTO.setMentoringSessions(
                mentoringSessionService.findAllMentoringSessionByKelompokOkkId(kelompokOkkDTO.getId())
                        .stream()
                        .map(mentoring -> {
                            MentoringSessionDTO mentoringDTO = modelMapper.map(mentoring, MentoringSessionDTO.class);
                            mentoringDTO.setMenteeIds(mentoring.getMentees().stream().map(Mentee::getId).toList());
                            return mentoringDTO;
                        })
                        .toList()
        );

        return kelompokOkkDTO;
    }
}

