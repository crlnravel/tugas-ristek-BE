package com.ristek.ristektugasbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="mentee")
public class Mentee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name = "jalur_masuk", nullable = false)
    private String jalurMasuk;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "kelompok_okk_id", nullable = false)
    private KelompokOkk kelompokOkk;

    @ManyToMany(mappedBy = "mentees")
    private List<MentoringSession> mentoringSessions;

}
