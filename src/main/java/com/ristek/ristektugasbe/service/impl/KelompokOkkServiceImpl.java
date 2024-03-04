package com.ristek.ristektugasbe.service.impl;

import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.Mentee;
import com.ristek.ristektugasbe.model.Mentor;
import com.ristek.ristektugasbe.model.Mentoring;
import com.ristek.ristektugasbe.repository.KelompokOkkRepository;
import com.ristek.ristektugasbe.repository.MenteeRepository;
import com.ristek.ristektugasbe.repository.MentorRepository;
import com.ristek.ristektugasbe.repository.MentoringRepository;
import com.ristek.ristektugasbe.service.KelompokOkkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KelompokOkkServiceImpl implements KelompokOkkService {

    private final KelompokOkkRepository kelompokOkkRepository;

    private final MenteeRepository menteeRepository;

    private final MentorRepository mentorRepository;

    private final MentoringRepository mentoringRepository;

    public KelompokOkkServiceImpl(KelompokOkkRepository kelompokOkkRepository, MenteeRepository menteeRepository, MentorRepository mentorRepository, MentoringRepository mentoringRepository) {
        this.kelompokOkkRepository = kelompokOkkRepository;
        this.menteeRepository = menteeRepository;
        this.mentorRepository = mentorRepository;
        this.mentoringRepository = mentoringRepository;
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
    public void deleteKelompokOkkById(Long id) {
        kelompokOkkRepository.deleteById(id);
    }

    @Override
    public List<Mentoring> findAllMentoring(Long id) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        if (kelompokOkk == null) {
            return null;
        }

        return kelompokOkk.getMentorings();
    }

    @Override
    public Mentoring findMentoringById(Long id, Long mentoringId) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        if (kelompokOkk == null) {
            return null;
        }

        Mentoring mentoring = mentoringRepository.findById(id).orElse(null);

        if (mentoring == null) {
            return null;
        }

        if (mentoring.getKelompokOkk() != kelompokOkk) {
            return null;
        }

        return mentoring;
    }

    @Override
    public Mentoring saveMentoring(Long id, Mentoring mentoring) {
        return null;
    }

    @Override
    public void deleteMentoringById(Long id, Long mentoringId) {

    }

    @Override
    public Mentor findMentor(Long id) {
        return null;
    }

    @Override
    public Mentor updateMentor(Long id, Mentoring mentoring) {
        return null;
    }

    @Override
    public List<Mentee> findAllMentee(Long id) {
        return null;
    }

    @Override
    public Mentee findMenteeById(Long id, Long menteeId) {
        return null;
    }

    @Override
    public Mentee saveMentee(Long id, Mentee mentee) {
        return null;
    }

    @Override
    public void deleteMenteeById(Long id, Long menteeId) {

    }
}
