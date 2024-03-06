package com.ristek.ristektugasbe.entity.acaraokk;

import com.ristek.ristektugasbe.entity.bph.Rapat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pembicara")
public class Pembicara {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nama")
    private String nama;

    @ManyToMany
    @JoinTable(
            name = "pembicara_acara",
            joinColumns = { @JoinColumn(name = "pembicara_id") },
            inverseJoinColumns = { @JoinColumn(name = "acara_id") }
    )
    private List<AcaraOkk> acaraOkkList;
}
