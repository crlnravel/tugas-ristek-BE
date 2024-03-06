package com.ristek.ristektugasbe.entity.bph;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="staff")
public class Staff {
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

    @ManyToOne
    @JoinColumn(name = "bph_id", nullable = false)
    private Bph bph;

    @ManyToMany
    @JoinTable(
            name = "staff_rapat",
            joinColumns = { @JoinColumn(name = "staff_id") },
            inverseJoinColumns = { @JoinColumn(name = "rapat_id") }
    )
    private List<Rapat> rapatList;
}
