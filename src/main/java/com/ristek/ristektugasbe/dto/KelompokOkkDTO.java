package com.ristek.ristektugasbe.dto;

import lombok.Data;

import java.util.List;

@Data
public class KelompokOkkDTO {
    private Long id;

    private String nomor;

    private MentorDTO mentor;

    private List<MenteeDTO> mentees;
    private List<MentoringDTO> mentoringSessions;
}
