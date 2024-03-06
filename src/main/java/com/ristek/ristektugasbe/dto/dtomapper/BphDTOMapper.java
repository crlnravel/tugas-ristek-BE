package com.ristek.ristektugasbe.dto.dtomapper;

import com.ristek.ristektugasbe.dto.bphrequest.RapatDTOReq;
import com.ristek.ristektugasbe.dto.bphrequest.StaffDTOReq;
import com.ristek.ristektugasbe.dto.bphresponse.BphDTORes;
import com.ristek.ristektugasbe.dto.bphresponse.RapatDTORes;
import com.ristek.ristektugasbe.dto.bphresponse.StaffDTORes;
import com.ristek.ristektugasbe.entity.bph.Bph;
import com.ristek.ristektugasbe.entity.bph.Rapat;
import com.ristek.ristektugasbe.entity.bph.Staff;
import com.ristek.ristektugasbe.service.BphService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BphDTOMapper {

    private final BphService bphService;
    private final ModelMapper modelMapper;

    public BphDTOMapper(BphService bphService, ModelMapper modelMapper) {
        this.bphService = bphService;
        this.modelMapper = modelMapper;
    }

    public BphDTORes convertToDto(Bph bph) {
        BphDTORes bphDTORes = modelMapper.map(bph, BphDTORes.class);

        List<Rapat> rapatList = bph.getRapatList();

        if (rapatList != null) {
            List<RapatDTORes> rapatDTOResList = bph
                    .getRapatList()
                    .stream()
                    .map(this::convertToDto)
                    .toList();

            bphDTORes.setRapatList(rapatDTOResList);
        }


        return bphDTORes;
    }

    public RapatDTORes convertToDto(Rapat rapat) {
        RapatDTORes rapatDTORes = modelMapper.map(rapat, RapatDTORes.class);

        rapatDTORes.setBphId(rapat.getBph().getId());

        rapatDTORes.setStaffHadir(rapat.getStaffList().stream().map(this::convertToDto).toList());

        return rapatDTORes;
    }

    public StaffDTORes convertToDto(Staff staff) {
        return modelMapper.map(staff, StaffDTORes.class);
    }


    public Rapat convertToEntity(RapatDTOReq rapatDTOReq) {
        Rapat rapat = modelMapper.map(rapatDTOReq, Rapat.class);

        List<Staff> staffList = rapatDTOReq
                .getStaffHadir()
                .stream()
                .map(bphService::findStaffById)
                .toList();

        rapat.setStaffList(staffList);

        return rapat;
    }

    public Staff convertToEntity(StaffDTOReq staffDTOReq) {
        return modelMapper.map(staffDTOReq, Staff.class);
    }

}
