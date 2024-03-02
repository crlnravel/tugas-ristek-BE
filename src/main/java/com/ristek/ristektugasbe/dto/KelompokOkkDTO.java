package com.ristek.ristektugasbe.dto;

import com.ristek.ristektugasbe.model.Mentor;
import com.ristek.ristektugasbe.model.MentoringSession;
import lombok.Data;

import java.util.List;

@Data
public class KelompokOkkDTO {
    private Long id;

    private String nomor;

    private MentorDTO mentor;

    private List<MenteeDTO> mentees;
    private List<MentoringSessionDTO> mentoringSessions;
}
