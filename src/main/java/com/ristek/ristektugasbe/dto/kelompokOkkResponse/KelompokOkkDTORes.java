package com.ristek.ristektugasbe.dto.kelompokokkresponse;

import lombok.Data;

import java.util.List;

@Data
public class KelompokOkkDTORes {
    private Long id;
    private String nomor;
    private MentorDTORes mentor;
    private List<MenteeDTORes> mentees;
    private List<MentoringDTORes> mentoringList;
}
