package com.ristek.ristektugasbe.service;

import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.Mentee;
import com.ristek.ristektugasbe.model.Mentor;
import com.ristek.ristektugasbe.model.Mentoring;

import java.util.List;

public interface KelompokOkkService {
    List<KelompokOkk> findAllKelompokOkk();
    KelompokOkk findKelompokOkkById(Long id);
    KelompokOkk saveKelompokOkk(KelompokOkk kelompokOkk);
    void deleteKelompokOkkById(Long id);
    List<Mentoring> findAllMentoring(Long id);
    Mentoring findMentoringById(Long id, Long mentoringId);
    Mentoring saveMentoring(Long id, Mentoring mentoring);
    void deleteMentoringById(Long id, Long mentoringId);
    Mentor findMentor(Long id);
    Mentor updateMentor(Long id, Mentoring mentoring);
    List<Mentee> findAllMentee(Long id);
    Mentee findMenteeById(Long id, Long menteeId);
    Mentee saveMentee(Long id, Mentee mentee);
    void deleteMenteeById(Long id, Long menteeId);
}
