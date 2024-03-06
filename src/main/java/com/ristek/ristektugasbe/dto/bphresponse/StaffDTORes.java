package com.ristek.ristektugasbe.dto.bphresponse;

import lombok.Data;

@Data
public class StaffDTORes {
    private Long id;
    private String nama;
    private String fakultas;
    private String jurusan;
    private Integer angkatan;
    private Long bphId;
}
