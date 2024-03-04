package com.ristek.ristektugasbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "kelompok_okk_id", nullable = false)
    private KelompokOkk kelompokOkk;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;

    @ManyToMany
    @JoinTable(
            name = "mentee_mentoring",
            joinColumns = { @JoinColumn(name = "mentee_id") },
            inverseJoinColumns = { @JoinColumn(name = "mentoring_session_id") }
    )
    private List<Mentoring> mentorings;

}
