package com.ristek.ristektugasbe.entity.acaraokk;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract")
public class Contract {

    @EmbeddedId
    private ContractKey id;

    @ManyToOne
    @MapsId("acaraOkkId")
    @JoinColumn(name = "acara_okk_id")
    private AcaraOkk acaraOkk;

    @ManyToOne
    @MapsId("sponsorId")
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    @ManyToOne
    @JoinColumn(name = "paket_sponsor_id")
    private PaketSponsor paketSponsor;
}
