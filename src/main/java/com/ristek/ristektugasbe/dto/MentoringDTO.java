package com.ristek.ristektugasbe.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MentoringDTO {
    private Long id;

    @Column(name = "waktu")
    private Timestamp waktu;

    private String tempat;

    private String materi;

    private Long kelompokOkkId;

    private List<Long> menteeIds;
}
