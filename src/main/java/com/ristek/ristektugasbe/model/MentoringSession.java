package com.ristek.ristektugasbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentoring_session")
public class MentoringSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "waktu")
    private Timestamp waktu;

    @Column(name = "tempat")
    private String tempat;

    @Column(name = "materi")
    private String materi;

    @ManyToOne
    @JoinColumn(name = "kelompok_okk_id", nullable = false)
    private KelompokOkk kelompokOkk;

    @ManyToMany
    @JoinTable(
            name = "mentoring_mentee",
            joinColumns = { @JoinColumn(name = "mentoring_session_id") },
            inverseJoinColumns = { @JoinColumn(name = "mentee_id") }
    )
    private List<Mentee> mentees;
}
