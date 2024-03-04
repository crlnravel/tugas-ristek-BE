package com.ristek.ristektugasbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentoring_session")
public class Mentoring {
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

    @ManyToMany(mappedBy = "mentoringSessions", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Mentee> mentees;
}
