package com.ristek.ristektugasbe.dto.kelompokokkrequest;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MentoringDTOReq {
    private Timestamp waktu;
    private String tempat;
    private String materi;
    private List<Long> menteeHadir;
}
