package com.ristek.ristektugasbe.dto.bphresponse;

import com.ristek.ristektugasbe.dto.kelompokokkresponse.MenteeDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MentorDTORes;
import com.ristek.ristektugasbe.dto.kelompokokkresponse.MentoringDTORes;
import lombok.Data;

import java.util.List;

@Data
public class BphDTORes {
    private Long id;
    private String nama;
    private List<StaffDTORes> staffList;
    private List<RapatDTORes> rapatList;
}
