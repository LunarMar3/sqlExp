package org.simple.dbms.Controller;

import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Department;
import org.simple.dbms.Service.DepartmentService;
import org.simple.dbms.Util.CommonResponse;
import org.simple.dbms.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getDepartmentList")
    public CommonResponse getDepartmentList(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        String role = jwtUtil.getRoleFromToken(token);
        PageDto pageDto = new PageDto(page, size);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return departmentService.getDepartmentList(pageDto);
    }
    @PostMapping("/updateDepartment")
    public CommonResponse updateDepartment(@RequestHeader("Authorization") String token, @RequestBody Department department) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return departmentService.updateDepartment(department);
    }
    @PostMapping("/insertDepartment")
    public CommonResponse insertDepartment(@RequestHeader("Authorization") String token, @RequestBody Department department) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return departmentService.insertDepartment(department);
    }
    @GetMapping("/deleteDepartment")
    public CommonResponse deleteDepartment(@RequestHeader("Authorization") String token, @RequestParam("departmentID") int departmentID) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return departmentService.deleteDepartment(departmentID);
    }

}
