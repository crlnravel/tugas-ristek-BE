package com.ristek.ristektugasbe.service.impl;

import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.Mentee;
import com.ristek.ristektugasbe.model.Mentor;
import com.ristek.ristektugasbe.model.MentoringSession;
import com.ristek.ristektugasbe.repository.KelompokOkkRepository;
import com.ristek.ristektugasbe.repository.MenteeRepository;
import com.ristek.ristektugasbe.repository.MentorRepository;
import com.ristek.ristektugasbe.repository.MentoringSessionRepository;
import com.ristek.ristektugasbe.service.KelompokOkkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KelompokOkkServiceImpl implements KelompokOkkService {

    private final KelompokOkkRepository kelompokOkkRepository;

    private final MenteeRepository menteeRepository;

    private final MentorRepository mentorRepository;

    private final MentoringSessionRepository mentoringSessionRepository;

    public KelompokOkkServiceImpl(KelompokOkkRepository kelompokOkkRepository, MenteeRepository menteeRepository, MentorRepository mentorRepository, MentoringSessionRepository mentoringSessionRepository) {
        this.kelompokOkkRepository = kelompokOkkRepository;
        this.menteeRepository = menteeRepository;
        this.mentorRepository = mentorRepository;
        this.mentoringSessionRepository = mentoringSessionRepository;
    }


    @Override
    public List<KelompokOkk> findAllKelompokOkk() {
        return kelompokOkkRepository.findAll();
    }

    @Override
    public KelompokOkk findKelompokOkkById(Long id) {
        return kelompokOkkRepository.findById(id).orElse(null);
    }

    @Override
    public KelompokOkk saveKelompokOkk(KelompokOkk kelompokOkk) {
        return kelompokOkkRepository.save(kelompokOkk);
    }

    @Override
    public KelompokOkk updateKelompokOkk(KelompokOkk kelompokOkk) {
        Mentor mentor = kelompokOkk.getMentor();
        mentor.setKelompokOkk(kelompokOkk);
        mentorRepository.save(mentor);

        List<Mentee> mentees = kelompokOkk.getMentees();
        mentees.forEach(mentee -> mentee.setKelompokOkk(kelompokOkk));
        menteeRepository.saveAll(mentees);

        return kelompokOkkRepository.save(kelompokOkk);
    }

    @Override
    public void deleteKelompokOkkById(Long id) {
        kelompokOkkRepository.deleteById(id);
    }

    @Override
    public KelompokOkk addMentee(Long id, Mentee mentee) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        List<Mentee> mentees = kelompokOkk.getMentees();
        mentees.add(mentee);

        mentee.setKelompokOkk(kelompokOkk);
        menteeRepository.save(mentee);

        kelompokOkk.setMentees(mentees);

        return kelompokOkkRepository.save(kelompokOkk);
    }

    @Override
    public Mentee findMenteeDetailById(Long id) {
        return menteeRepository.findById(id).orElse(null);
    }

    @Override
    public KelompokOkk addMentoringSession(Long id, MentoringSession mentoringSession) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        mentoringSession.setKelompokOkk(kelompokOkk);

        List<MentoringSession> mentoringSessions = kelompokOkk.getMentoringSessions();
        mentoringSessions.add(mentoringSession);
        kelompokOkk.setMentoringSessions(mentoringSessions);

        return kelompokOkkRepository.save(kelompokOkk);
    }

    @Override
    public KelompokOkk updateMentoringSession(Long id, MentoringSession mentoringSession) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        mentoringSession.setKelompokOkk(kelompokOkk);

        List<MentoringSession> mentoringSessions = kelompokOkk.getMentoringSessions();
        mentoringSessions.add(mentoringSession);
        kelompokOkk.setMentoringSessions(mentoringSessions);

        return kelompokOkkRepository.save(kelompokOkk);
    }
}
