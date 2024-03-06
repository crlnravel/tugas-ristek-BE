package com.ristek.ristektugasbe.service.impl;

import com.ristek.ristektugasbe.entity.kelompokokk.KelompokOkk;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentee;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentor;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentoring;
import com.ristek.ristektugasbe.repository.kelompokokk.KelompokOkkRepository;
import com.ristek.ristektugasbe.repository.kelompokokk.MenteeRepository;
import com.ristek.ristektugasbe.repository.kelompokokk.MentorRepository;
import com.ristek.ristektugasbe.repository.kelompokokk.MentoringRepository;
import com.ristek.ristektugasbe.service.KelompokOkkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KelompokOkkServiceImpl implements KelompokOkkService {

    private final KelompokOkkRepository kelompokOkkRepository;

    private final MenteeRepository menteeRepository;

    private final MentoringRepository mentoringRepository;

    public KelompokOkkServiceImpl(KelompokOkkRepository kelompokOkkRepository, MenteeRepository menteeRepository, MentorRepository mentorRepository, MentoringRepository mentoringRepository) {
        this.kelompokOkkRepository = kelompokOkkRepository;
        this.menteeRepository = menteeRepository;
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
        Mentor mentor = kelompokOkk.getMentor();

        mentor.setKelompokOkk(kelompokOkk);

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

        return kelompokOkk.getMentoringList();
    }

    @Override
    public Mentoring findMentoringById(Long mentoringId) {
        return mentoringRepository.findById(mentoringId).orElse(null);
    }

    @Override
    public Mentoring saveMentoring(Long id, Mentoring mentoring) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        if (kelompokOkk == null) {
            return null;
        }

        mentoring.setKelompokOkk(kelompokOkk);

        List<Mentoring> mentoringList = kelompokOkk.getMentoringList();
        mentoringList.add(mentoring);
        kelompokOkk.setMentoringList(mentoringList);

        kelompokOkkRepository.save(kelompokOkk);

        List<Mentee> menteeList = mentoring.getMentees();
        menteeList.forEach(
                mentee -> {
                    List<Mentoring> mentoringList_ = mentee.getMentorings();
                    mentoringList_.add(mentoring);
                    mentee.setMentorings(mentoringList_);
                }
        );

        mentoring.setMentees(menteeList);

        return mentoringRepository.save(mentoring);
    }

    @Override
    public Mentoring addPesertaMentoring(Long id, Mentoring mentoring, Mentee mentee) {
        List<Mentoring> mentoringList = mentee.getMentorings();
        mentoringList.add(mentoring);
        mentee.setMentorings(mentoringList);

        List<Mentee> menteeList = mentoring.getMentees();
        menteeList.add(mentee);
        mentoring.setMentees(menteeList);

        return mentoringRepository.save(mentoring);
    }

    @Override
    public void deletePesertaMentoring(Long id, Mentoring mentoring, Mentee mentee) {
        List<Mentoring> mentoringList = mentee.getMentorings();
        mentoringList.remove(mentoring);
        mentee.setMentorings(mentoringList);

        List<Mentee> menteeList = mentoring.getMentees();
        menteeList.remove(mentee);
        mentoring.setMentees(menteeList);
    }

    @Override
    public void deleteMentoringById(Long mentoringId) {
        mentoringRepository.deleteById(mentoringId);
    }

    @Override
    public Mentor findMentor(Long id) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        if (kelompokOkk == null) {
            return null;
        }

        return kelompokOkk.getMentor();
    }

    @Override
    public Mentor updateMentor(Long id, Mentor mentor) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        if (kelompokOkk == null) {
            return null;
        }

        kelompokOkk.setMentor(mentor);

        return kelompokOkkRepository.save(kelompokOkk).getMentor();
    }

    @Override
    public List<Mentee> findAllMentee(Long id) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        if (kelompokOkk == null) {
            return null;
        }

        return kelompokOkk.getMentees();
    }

    @Override
    public Mentee findMenteeById(Long menteeId) {
        return menteeRepository.findById(menteeId).orElse(null);
    }

    @Override
    public Mentee saveMentee(Long id, Mentee mentee) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(id).orElse(null);

        if (kelompokOkk == null) {
            return null;
        }

        mentee.setKelompokOkk(kelompokOkk);

        List<Mentee> menteeList = kelompokOkk.getMentees();
        menteeList.add(mentee);
        kelompokOkk.setMentees(menteeList);


        kelompokOkkRepository.save(kelompokOkk);

        return menteeRepository.save(mentee);
    }

    @Override
    public void deleteMenteeById(Long menteeId) {
        menteeRepository.deleteById(menteeId);
    }
}
