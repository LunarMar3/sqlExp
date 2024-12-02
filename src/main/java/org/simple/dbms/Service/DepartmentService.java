package org.simple.dbms.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Department;
import org.simple.dbms.Mapper.DepartmentMapper;
import org.simple.dbms.Util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public CommonResponse getDepartmentList(PageDto pageDto) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Department> page1 = new Page<>(page, size);
        IPage<Department> resultPage = departmentMapper.selectPage(page1, null);
        return new CommonResponse(200, "获取成功", departmentMapper.selectPage(resultPage, null));
    }

    public CommonResponse updateDepartment(Department department) {
        departmentMapper.updateById(department);
        return new CommonResponse(200, "更新成功", null);
    }

    public CommonResponse insertDepartment(Department department) {
        departmentMapper.insert(department);
        return new CommonResponse(200, "插入成功", null);
    }

    public CommonResponse deleteDepartment(int departmentID) {
        departmentMapper.deleteById(departmentID);
        return new CommonResponse(200, "删除成功", null);
    }
}
