package com.ristek.ristektugasbe.entity.acaraokk;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paket_sponsor")
public class PaketSponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipe")
    private String tipe;

    @Column(name = "harga")
    private Double harga;

    @Column(name = "benefit", columnDefinition = "TEXT")
    private String benefit;

    @OneToMany(mappedBy = "paketSponsor")
    private List<Contract> contracts;
}
