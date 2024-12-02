package org.simple.dbms.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simple.dbms.Dao.AccountDao;
import org.simple.dbms.Dao.DoctorDao;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Doctor;
import org.simple.dbms.Mapper.DoctorMapper;
import org.simple.dbms.Util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    public String insert(AccountDao accountDao) {
        Doctor doctor = new Doctor();
        doctor.setName(accountDao.getName());
        doctor.setGender(accountDao.getGender());
        doctor.setDepartmentID(accountDao.getDepartmentID());
        doctor.setContactNumber(accountDao.getContactNumber());
        doctor.setEmail(accountDao.getEmail());
        doctor.setVerified(0);
        doctorMapper.insert(doctor);
        return doctor.getDoctorID();
    }

    public CommonResponse getDoctorList(PageDto pageDto) {
        Integer page = pageDto.getPage();
        Integer Size = pageDto.getSize();
        Page<Doctor> page1 = new Page<>(page, Size);
        return new CommonResponse(200, "获取成功", doctorMapper.selectPage(page1, null));
    }

    public void updateDoctor(DoctorDao doctorDao) {
        Doctor doctor = new Doctor();
        doctor.setDoctorID(doctorDao.getDoctorID());
        doctor.setName(doctorDao.getName());
        doctor.setGender(doctorDao.getGender());
        doctor.setContactNumber(doctorDao.getContactNumber());
        doctor.setEmail(doctorDao.getEmail());
        if (doctorDao.getVerified() != null) {
            doctor.setVerified(doctorDao.getVerified());
        }
        if (doctorDao.getDepartmentID() != null) {
            doctor.setDepartmentID(doctorDao.getDepartmentID());
        }

        doctorMapper.updateById(doctor);
    }

    public CommonResponse getDoctor(String userID) {
        return new CommonResponse(200, "获取成功", doctorMapper.selectById(userID));
    }

    public void deleteDoctor(String doctorID) {
        doctorMapper.deleteById(doctorID);
    }

    public CommonResponse searchDoctors(PageDto pageDto, String keyword) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Doctor> page1 = new Page<>(page, size);

        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("Name", keyword)
                .or()
                .like("DepartmentID", keyword)
                .or()
                .like("Email", keyword);

        return new CommonResponse(200, "获取成功", doctorMapper.selectPage(page1, queryWrapper));
    }
}
