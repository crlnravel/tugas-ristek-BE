package com.ristek.ristektugasbe.entity.bph;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="rapat")
public class Rapat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "waktu")
    private Timestamp waktu;

    @Column(name = "tempat")
    private String tempat;

    @Column(name = "kesimpulan", columnDefinition = "TEXT")
    private String kesimpulan;

    @ManyToOne
    @JoinColumn(name = "bph_id", nullable = false)
    private Bph bph;

    @ManyToMany(mappedBy = "rapatList", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Staff> staffList;
}
