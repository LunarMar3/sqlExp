package org.simple.dbms.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.simple.dbms.Entity.Patient;
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
