package com.ristek.ristektugasbe.dto.bphrequest;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RapatDTOReq {
    private Timestamp waktu;
    private String tempat;
    private String kesimpulan;
    private List<Long> staffHadir;
}
