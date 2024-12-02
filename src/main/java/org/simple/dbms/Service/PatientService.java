package org.simple.dbms.Service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simple.dbms.Dao.AccountDao;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Dao.PatientDao;
import org.simple.dbms.Entity.Patient;
import org.simple.dbms.Util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.simple.dbms.Mapper.PatientMapper;
@Service
public class PatientService {
    @Autowired
    private PatientMapper patientMapper;

    public String insert(AccountDao accountDao) {
        Patient patient = new Patient();
        patient.setName(accountDao.getName());
        patient.setGender(accountDao.getGender());
        patient.setContactNumber(accountDao.getContactNumber());
        patient.setDateOfBirth(accountDao.getDateOfBirth());
        patient.setAddress(accountDao.getAddress());
        patientMapper.insert(patient);
        return patient.getPatientID();
    }

    public CommonResponse getPatientList(PageDto pageDto) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Patient> page1 = new Page<>(page, size,true);
        IPage<Patient> resultPage = patientMapper.selectPage(page1, null);
        return new CommonResponse(200, "获取成功", resultPage);
    }

    public void updatePatient(PatientDao patientDao) {
        Patient patient = new Patient();
        BeanUtil.copyProperties(patientDao, patient);
        patientMapper.updateById(patient);
    }

    public CommonResponse getPatient(String userID) {
        return new CommonResponse(200, "获取成功", patientMapper.selectById(userID));
    }

    public void deletePatient(String userID) {
        patientMapper.deleteById(userID);
    }

    public CommonResponse searchPatients(PageDto pageDto, String keyword) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Patient> page1 = new Page<>(page, size);
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("Name", keyword)
                .or()
                .like("Address", keyword)
                .or()
                .like("ContactNumber", keyword);
        IPage<Patient> resultPage = patientMapper.selectPage(page1, queryWrapper);
        return new CommonResponse(200, "获取成功", resultPage);
    }
}
