package com.ristek.ristektugasbe.service;

import com.ristek.ristektugasbe.model.MentoringSession;

import java.util.List;

public interface MentoringSessionService {
    List<MentoringSession> findAllMentoringSessionByKelompokOkkId(Long kelompokOkkId);
    MentoringSession findMentoringSessionById(Long id);
    void deleteMentoringSessionById(Long id);
}
