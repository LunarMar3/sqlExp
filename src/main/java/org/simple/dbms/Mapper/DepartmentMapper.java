package org.simple.dbms.Mapper;
import org.apache.ibatis.annotations.Mapper;
import org.simple.dbms.Entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}
