package org.simple.dbms.Controller;

import org.simple.dbms.Entity.Nurse;
import org.simple.dbms.Service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.simple.dbms.Util.CommonResponse;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Util.JwtUtil;

@RestController
@RequestMapping("/nurse")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getNurseList")
    public CommonResponse getNurseList(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return nurseService.getNurseList(pageDto);
    }

    @PostMapping("/updateNurse")
    public CommonResponse updateNurse(@RequestHeader("Authorization") String token, @RequestBody Nurse nurse) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return nurseService.updateNurse(nurse);
    }
    @GetMapping("/deleteNurse")
    public CommonResponse deleteNurse(@RequestHeader("Authorization") String token, @RequestParam("nurseID") int nurseID) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return nurseService.deleteNurse(nurseID);
    }
    @PostMapping("/insertNurse")
    public CommonResponse insertNurse(@RequestHeader("Authorization") String token, @RequestBody Nurse nurse) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return nurseService.insertNurse(nurse);
    }
    @GetMapping("/searchNurses")
    public CommonResponse search(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("keyword") String keyword) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return nurseService.search(pageDto, keyword);
    }
}
