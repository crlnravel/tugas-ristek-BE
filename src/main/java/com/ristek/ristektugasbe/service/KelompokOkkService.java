package com.ristek.ristektugasbe.service;

import com.ristek.ristektugasbe.entity.kelompokokk.KelompokOkk;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentee;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentor;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentoring;

import java.util.List;

public interface KelompokOkkService {
    List<KelompokOkk> findAllKelompokOkk();
    KelompokOkk findKelompokOkkById(Long id);
    KelompokOkk saveKelompokOkk(KelompokOkk kelompokOkk);
    void deleteKelompokOkkById(Long id);
    List<Mentoring> findAllMentoring(Long id);
    Mentoring findMentoringById(Long mentoringId);
    Mentoring saveMentoring(Long id, Mentoring mentoring);
    Mentoring addPesertaMentoring(Long id, Mentoring mentoring, Mentee mentee);
    void deletePesertaMentoring(Long id, Mentoring mentoring, Mentee mentee);
    void deleteMentoringById(Long mentoringId);
    Mentor findMentor(Long id);
    Mentor updateMentor(Long id, Mentor mentor);
    List<Mentee> findAllMentee(Long id);
    Mentee findMenteeById(Long menteeId);
    Mentee saveMentee(Long id, Mentee mentee);
    void deleteMenteeById(Long menteeId);
}
