package com.ristek.ristektugasbe.service.impl;

import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.MentoringSession;
import com.ristek.ristektugasbe.repository.KelompokOkkRepository;
import com.ristek.ristektugasbe.repository.MentoringSessionRepository;
import com.ristek.ristektugasbe.service.MentoringSessionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentoringSessionServiceImpl implements MentoringSessionService {

    private final KelompokOkkRepository kelompokOkkRepository;
    private final MentoringSessionRepository mentoringSessionRepository;

    public MentoringSessionServiceImpl(KelompokOkkRepository kelompokOkkRepository, MentoringSessionRepository mentoringSessionRepository) {
        this.kelompokOkkRepository = kelompokOkkRepository;
        this.mentoringSessionRepository = mentoringSessionRepository;
    }

    @Override
    public List<MentoringSession> findAllMentoringSessionByKelompokOkkId(Long kelompokOkkId) {
        KelompokOkk kelompokOkk = kelompokOkkRepository.findById(kelompokOkkId).orElse(null);

        return kelompokOkk.getMentoringSessions();
    }

    @Override
    public MentoringSession findMentoringSessionById(Long id) {
        return mentoringSessionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMentoringSessionById(Long id) {
        mentoringSessionRepository.deleteById(id);
    }
}
