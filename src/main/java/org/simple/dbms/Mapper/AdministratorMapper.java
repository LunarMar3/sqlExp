package org.simple.dbms.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.simple.dbms.Entity.Administrator;

@Mapper
public interface AdministratorMapper extends BaseMapper<Administrator> {
}
