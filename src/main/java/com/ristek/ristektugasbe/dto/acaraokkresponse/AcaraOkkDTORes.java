package com.ristek.ristektugasbe.dto.acaraokkresponse;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class AcaraOkkDTORes {
    private Long id;
    private String nama;
    private Timestamp waktu;
    private String tempat;
    private List<PembicaraDTORes> pembicaraList;
    private List<ContractDTORes> contracts;
}
