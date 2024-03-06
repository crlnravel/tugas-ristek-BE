package com.ristek.ristektugasbe.entity.acaraokk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractKey implements Serializable {

    @Column(name = "acara_okk_id")
    private Long acaraOkkId;

    @Column(name = "sponsor_id")
    private Long sponsorId;
}