package org.simple.dbms.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.simple.dbms.Entity.Treatment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
@Mapper
public interface TreatmentMapper extends BaseMapper<Treatment> {
}
