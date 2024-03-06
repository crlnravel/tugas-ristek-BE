package com.ristek.ristektugasbe.dto.acaraokkresponse;

import lombok.Data;

import java.util.List;

@Data
public class SponsorDTORes {
    private Long id;
    private String nama;
    private List<ContractDTORes> contracts;
}
