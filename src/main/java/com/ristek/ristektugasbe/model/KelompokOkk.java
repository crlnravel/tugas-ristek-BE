package com.ristek.ristektugasbe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kelompok_okk")
public class KelompokOkk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nomor", unique = true)
    private String nomor;

    @OneToOne(mappedBy = "kelompokOkk", cascade = CascadeType.ALL)
    private Mentor mentor;

    @OneToMany(mappedBy = "kelompokOkk")
    private List<Mentee> mentees;

    @OneToMany(mappedBy = "kelompokOkk")
    private List<Mentoring> mentorings;
}
