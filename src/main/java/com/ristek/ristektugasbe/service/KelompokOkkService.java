package com.ristek.ristektugasbe.service;

import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.Mentee;
import com.ristek.ristektugasbe.model.Mentor;
import com.ristek.ristektugasbe.model.MentoringSession;

import java.util.List;

public interface KelompokOkkService {
    List<KelompokOkk> findAllKelompokOkk();
    KelompokOkk findKelompokOkkById(Long id);
    KelompokOkk saveKelompokOkk(KelompokOkk kelompokOkk);
    KelompokOkk updateKelompokOkk(KelompokOkk kelompokOkk);
    void deleteKelompokOkkById(Long id);
    KelompokOkk addMentee(Long id, Mentee mentee);
    Mentee findMenteeDetailById(Long id);
    KelompokOkk addMentoringSession(Long id, MentoringSession mentoringSession);
    KelompokOkk updateMentoringSession(Long id, MentoringSession mentoringSession);

}
