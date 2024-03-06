package com.ristek.ristektugasbe.dto.bphresponse;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RapatDTORes {
    private Long id;
    private Timestamp waktu;
    private String tempat;
    private String kesimpulan;
    private Long bphId;
    private List<StaffDTORes> staffHadir;
}
