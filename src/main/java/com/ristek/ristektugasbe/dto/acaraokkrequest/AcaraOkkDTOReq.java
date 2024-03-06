package com.ristek.ristektugasbe.dto.acaraokkrequest;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AcaraOkkDTOReq {
    private String nama;
    private Timestamp waktu;
    private String tempat;
}
