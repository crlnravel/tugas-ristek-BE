package com.ristek.ristektugasbe.dto.kelompokokkresponse;

import lombok.Data;

@Data
public class MentorDTORes {
    private Long id;
    private String nama;
    private String fakultas;
    private String jurusan;
    private Integer angkatan;
    private Long kelompokOkkId;
}
