package com.ristek.ristektugasbe.service.impl;

import com.ristek.ristektugasbe.entity.bph.Bph;
import com.ristek.ristektugasbe.entity.bph.Rapat;
import com.ristek.ristektugasbe.entity.bph.Staff;
import com.ristek.ristektugasbe.repository.bph.BphRepository;
import com.ristek.ristektugasbe.repository.bph.RapatRepository;
import com.ristek.ristektugasbe.repository.bph.StaffRepository;
import com.ristek.ristektugasbe.service.BphService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BphServiceImpl implements BphService {

    private final BphRepository bphRepository;
    private final RapatRepository rapatRepository;
    private final StaffRepository staffRepository;

    public BphServiceImpl(BphRepository bphRepository, RapatRepository rapatRepository, StaffRepository staffRepository) {
        this.bphRepository = bphRepository;
        this.rapatRepository = rapatRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public List<Bph> findAllBph() {
        return bphRepository.findAll();
    }

    @Override
    public Bph findBphById(Long id) {
        return bphRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rapat> findAllRapat(Long id) {
        Bph bph = bphRepository.findById(id).orElse(null);

        if (bph == null) {
            return null;
        }

        return bph.getRapatList();
    }

    @Override
    public Rapat findRapatById(Long rapatId) {
        return rapatRepository.findById(rapatId).orElse(null);
    }

    @Override
    public Rapat saveRapat(Long id, Rapat rapat) {
        Bph bph = bphRepository.findById(id).orElse(null);

        if (bph == null) {
            return null;
        }

        rapat.setBph(bph);

        List<Rapat> rapatList = bph.getRapatList();
        rapatList.add(rapat);
        bph.setRapatList(rapatList);

        bphRepository.save(bph);

        List<Staff> staffList = rapat.getStaffList();
        staffList.forEach(
                staff -> {
                    List<Rapat> rapatList_ = staff.getRapatList();
                    rapatList_.add(rapat);
                    staff.setRapatList(rapatList_);
                }
        );

        rapat.setStaffList(staffList);

        return rapatRepository.save(rapat);
    }

    @Override
    public Rapat addPesertaRapat(Long id, Rapat rapat, Staff staff) {
        List<Rapat> rapatList = staff.getRapatList();
        rapatList.add(rapat);
        staff.setRapatList(rapatList);

        List<Staff> staffList = rapat.getStaffList();
        staffList.add(staff);
        rapat.setStaffList(staffList);

        return rapatRepository.save(rapat);
    }

    @Override
    public void deletePesertaRapat(Long id, Rapat rapat, Staff staff) {
        List<Rapat> rapatList = staff.getRapatList();
        rapatList.remove(rapat);
        staff.setRapatList(rapatList);

        List<Staff> staffList = rapat.getStaffList();
        staffList.remove(staff);
        rapat.setStaffList(staffList);
    }

    @Override
    public void deleteRapatById(Long rapatId) {
        rapatRepository.deleteById(rapatId);
    }

    @Override
    public List<Staff> findAllStaff(Long id) {
        Bph bph = bphRepository.findById(id).orElse(null);

        if (bph == null) {
            return null;
        }

        return bph.getStaffList();
    }

    @Override
    public Staff findStaffById(Long staffId) {
        return staffRepository.findById(staffId).orElse(null);
    }

    @Override
    public Staff saveStaff(Long id, Staff staff) {
        Bph bph = bphRepository.findById(id).orElse(null);

        if (bph == null) {
            return null;
        }

        staff.setBph(bph);

        List<Staff> staffList = bph.getStaffList();
        staffList.add(staff);
        bph.setStaffList(staffList);


        bphRepository.save(bph);

        return staffRepository.save(staff);
    }

    @Override
    public void deleteStaffById(Long staffId) {
        staffRepository.deleteById(staffId);
    }
}
