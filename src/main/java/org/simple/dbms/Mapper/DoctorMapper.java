package org.simple.dbms.Mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.simple.dbms.Entity.Doctor;

@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
}
