package com.ristek.ristektugasbe.dto.dtomapper;

import com.ristek.ristektugasbe.dto.kelompokokkrequest.KelompokOkkDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkrequest.MenteeDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkrequest.MentorDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkrequest.MentoringDTOReq;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.KelompokOkkDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MenteeDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MentorDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MentoringDTORes;
import com.ristek.ristektugasbe.entity.kelompokokk.KelompokOkk;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentee;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentor;
import com.ristek.ristektugasbe.entity.kelompokokk.Mentoring;
import com.ristek.ristektugasbe.service.KelompokOkkService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KelompokOkkDTOMapper {
    /*
     * Mapper class, digunakan untuk memudahkan mapping dari DTO ke entity dan sebaliknya.
     *
     */

    private final KelompokOkkService kelompokOkkService;
    private final ModelMapper modelMapper;

    public KelompokOkkDTOMapper(KelompokOkkService kelompokOkkService, ModelMapper modelMapper) {
        this.kelompokOkkService = kelompokOkkService;
        this.modelMapper = modelMapper;
    }

    public KelompokOkkDTORes convertToDto(KelompokOkk kelompokOkk) {
        KelompokOkkDTORes kelompokOkkDTORes = modelMapper.map(kelompokOkk, KelompokOkkDTORes.class);

        List<Mentoring> mentoringList = kelompokOkk.getMentoringList();

        if (mentoringList != null) {
            List<MentoringDTORes> mentoringDTOResList = kelompokOkk
                    .getMentoringList()
                    .stream()
                    .map(this::convertToDto)
                    .toList();

            kelompokOkkDTORes.setMentoringList(mentoringDTOResList);
        }


        return kelompokOkkDTORes;
    }

    public MentorDTORes convertToDto(Mentor mentor) {
        MentorDTORes mentorDTORes = modelMapper.map(mentor, MentorDTORes.class);

        mentorDTORes.setKelompokOkkId(mentor.getKelompokOkk().getId());

        return mentorDTORes;
    }

    public MentoringDTORes convertToDto(Mentoring mentoring) {
        MentoringDTORes mentoringDTORes = modelMapper.map(mentoring, MentoringDTORes.class);

        MentorDTORes mentorDTORes = this.convertToDto(mentoring.getKelompokOkk().getMentor());

        mentoringDTORes.setKelompokOkkId(mentoring.getKelompokOkk().getId());

        mentoringDTORes.setMentor(mentorDTORes);
        mentoringDTORes.setMenteeHadir(mentoring.getMentees().stream().map(this::convertToDto).toList());

        return mentoringDTORes;
    }

    public MenteeDTORes convertToDto(Mentee mentee) {
        return modelMapper.map(mentee, MenteeDTORes.class);
    }


    public KelompokOkk convertToEntity(KelompokOkkDTOReq kelompokOkkDTOReq) {
        return modelMapper.map(kelompokOkkDTOReq, KelompokOkk.class);
    }

    public Mentor convertToEntity(MentorDTOReq mentorDTOReq) {
        return modelMapper.map(mentorDTOReq, Mentor.class);
    }

    public Mentoring convertToEntity(MentoringDTOReq mentoringDTOReq) {
        Mentoring mentoring = modelMapper.map(mentoringDTOReq, Mentoring.class);

        List<Mentee> menteeList = mentoringDTOReq
                .getMenteeHadir()
                .stream()
                .map(kelompokOkkService::findMenteeById)
                .toList();

        mentoring.setMentees(menteeList);

        return mentoring;
    }

    public Mentee convertToEntity(MenteeDTOReq menteeDTOReq) {
        return modelMapper.map(menteeDTOReq, Mentee.class);
    }
}
