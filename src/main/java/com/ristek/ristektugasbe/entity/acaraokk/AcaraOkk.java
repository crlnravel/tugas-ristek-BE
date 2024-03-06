package com.ristek.ristektugasbe.entity.acaraokk;

import com.ristek.ristektugasbe.entity.bph.Staff;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "acara_okk")
public class AcaraOkk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "waktu")
    private Timestamp waktu;

    @Column(name = "tempat")
    private String tempat;

    @ManyToMany(mappedBy = "acaraOkkList", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Pembicara> pembicaraList;

    @OneToMany(mappedBy = "acaraOkk")
    private List<Contract> contracts;
}
