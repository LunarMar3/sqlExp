package org.simple.dbms.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Treatment;
import org.simple.dbms.Util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.simple.dbms.Mapper.TreatmentMapper;
@Service
public class TreatmentService {
    @Autowired
    private TreatmentMapper treatmentMapper;


    public CommonResponse getTreatmentList(PageDto pageDto) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Treatment> page1 = new Page<>(page, size);
        return new CommonResponse(200, "获取成功", treatmentMapper.selectPage(page1, null));
    }

    public CommonResponse getTreatment(PageDto pageDto,String doctorID) {
        Integer page =  pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Treatment> page1 = new Page<>(page, size);
        QueryWrapper<Treatment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DoctorID", doctorID);
        return new CommonResponse(200, "获取成功", treatmentMapper.selectPage(page1, queryWrapper));
    }

    public CommonResponse updateTreatment(Treatment treatment) {
        treatmentMapper.updateById(treatment);
        return new CommonResponse(200, "更新成功", null);
    }

    public CommonResponse deleteTreatment(int treatmentID) {
        treatmentMapper.deleteById(treatmentID);
        return new CommonResponse(200, "删除成功", null);
    }

    public CommonResponse insertTreatment(Treatment treatment) {
        treatmentMapper.insert(treatment);
        return new CommonResponse(200, "插入成功", null);
    }
    public CommonResponse search(PageDto pageDto, String keyword) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Treatment> page1 = new Page<>(page, size);
        QueryWrapper<Treatment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("TreatmentID", keyword)
                .or()
                .like("DoctorID", keyword)
                .or()
                .like("PatientID", keyword)
                .or()
                .like("TreatmentDate", keyword);
        return new CommonResponse(200, "获取成功", treatmentMapper.selectPage(page1, queryWrapper));
    }

    public CommonResponse getTreatmentPa(PageDto pageDto, String patientID) {
        Integer page =  pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Treatment> page1 = new Page<>(page, size);
        QueryWrapper<Treatment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PatientID", patientID);
        return new CommonResponse(200, "获取成功", treatmentMapper.selectPage(page1, queryWrapper));
    }
}
