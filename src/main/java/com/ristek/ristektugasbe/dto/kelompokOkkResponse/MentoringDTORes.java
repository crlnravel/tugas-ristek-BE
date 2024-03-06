package com.ristek.ristektugasbe.dto.kelompokokkresponse;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MentoringDTORes {
    private Long id;
    private Timestamp waktu;
    private String tempat;
    private String materi;
    private Long kelompokOkkId;
    private MentorDTORes mentor;
    private List<MenteeDTORes> menteeHadir;
}
