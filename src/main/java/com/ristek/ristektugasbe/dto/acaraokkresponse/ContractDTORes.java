package com.ristek.ristektugasbe.dto.acaraokkresponse;

import lombok.Data;

@Data
public class ContractDTORes {
    private Long acaraOkkId;
    private Long sponsorId;
    private PaketSponsorDTORes paketSponsor;
}
