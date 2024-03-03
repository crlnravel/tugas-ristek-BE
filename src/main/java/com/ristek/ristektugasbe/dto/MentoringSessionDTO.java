package com.ristek.ristektugasbe.dto;

import com.ristek.ristektugasbe.model.KelompokOkk;
import com.ristek.ristektugasbe.model.Mentee;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
public class MentoringSessionDTO {
    private Long id;

    @Column(name = "waktu")
    private Timestamp waktu;

    private String tempat;

    private String materi;

    private Long kelompokOkkId;

    private List<Long> menteeIds;
}
