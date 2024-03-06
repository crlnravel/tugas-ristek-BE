package com.ristek.ristektugasbe.dto.kelompokokkrequest;

import com.ristek.ristektugasbe.dto.kelompokokkresponse.MentorDTORes;
import lombok.Data;

@Data
public class KelompokOkkDTOReq {
    private String nomor;
    private MentorDTORes mentor;
}
