package com.ristek.ristektugasbe.service;

import com.ristek.ristektugasbe.entity.bph.Bph;
import com.ristek.ristektugasbe.entity.bph.Rapat;
import com.ristek.ristektugasbe.entity.bph.Staff;

import java.util.List;

public interface BphService {
    List<Bph> findAllBph();
    Bph findBphById(Long id);
    List<Rapat> findAllRapat(Long id);
    Rapat findRapatById(Long rapatId);
    Rapat saveRapat(Long id, Rapat rapat);
    Rapat addPesertaRapat(Long id, Rapat rapat, Staff staff);
    void deletePesertaRapat(Long id, Rapat rapat, Staff staff);
    void deleteRapatById(Long rapatId);
    List<Staff> findAllStaff(Long id);
    Staff findStaffById(Long staffId);
    Staff saveStaff(Long id, Staff staff);
    void deleteStaffById(Long staffId);
}
