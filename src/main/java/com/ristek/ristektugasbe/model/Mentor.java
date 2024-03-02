package com.ristek.ristektugasbe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name = "fakultas", nullable = false)
    private String fakultas;

    @Column(name = "jurusan", nullable = false)
    private String jurusan;

    @Column(name = "angkatan", nullable = false)
    private Integer angkatan;

    @OneToOne
    @JoinColumn(name = "kelompok_id", referencedColumnName = "id", nullable = false)
    private KelompokOkk kelompokOkk;
}
