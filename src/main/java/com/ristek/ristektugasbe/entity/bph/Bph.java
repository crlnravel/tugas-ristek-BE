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
@Table(name = "bph")
public class Bph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nama", unique = true)
    private String nama;

    @OneToMany(mappedBy = "bph")
    private List<Staff> staffList;

    @OneToMany(mappedBy = "bph")
    private List<Rapat> rapatList;
}
