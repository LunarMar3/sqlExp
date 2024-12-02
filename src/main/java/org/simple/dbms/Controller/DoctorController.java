package org.simple.dbms.Controller;

import org.simple.dbms.Dao.DoctorDao;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Util.CommonResponse;
import org.simple.dbms.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.simple.dbms.Service.DoctorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/getDoctorList")
    public CommonResponse getDoctorList(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        String role = jwtUtil.getRoleFromToken(token);
        PageDto pageDto = new PageDto(page, size);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return doctorService.getDoctorList(pageDto);
    }

    @GetMapping("/getSelf")
    public CommonResponse getSelf(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        if (!redisTemplate.hasKey(token)) {
            return new CommonResponse(500, "未登录", null);
        }
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Doctor")) {
            return new CommonResponse(500, "无权限", null);
        }
        return doctorService.getDoctor(jwtUtil.getDoctorID(token));
    }


    @PostMapping("/updateDoctor")
    public CommonResponse updateDoctor(@RequestBody DoctorDao doctorDao, @RequestHeader("Authorization") String token) {
        if (!redisTemplate.hasKey(token)) {
            return new CommonResponse(500, "未登录", null);
        }
        String role = jwtUtil.getRoleFromToken(token);
        if (role.equals("Administrator")) {
            doctorService.updateDoctor(doctorDao);
        } else if (role.equals("Doctor")) {
            DoctorDao newDoctorDao = new DoctorDao();
            newDoctorDao.setDoctorID(jwtUtil.getDoctorID(token));
            newDoctorDao.setContactNumber(doctorDao.getContactNumber());
            newDoctorDao.setEmail(doctorDao.getEmail());
            newDoctorDao.setName(doctorDao.getName());
            newDoctorDao.setGender(doctorDao.getGender());
            doctorService.updateDoctor(newDoctorDao);
        } else {
            return new CommonResponse(500, "无权限", null);
        }
        return new CommonResponse(200, "更新成功", null);
    }

    @GetMapping("/deleteDoctor")
    public CommonResponse deleteDoctor(@RequestParam String doctorID, @RequestHeader("Authorization") String token) {
        if (!redisTemplate.hasKey(token)) {
            return new CommonResponse(500, "未登录", null);
        }
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        doctorService.deleteDoctor(doctorID);
        return new CommonResponse(200, "删除成功", null);
    }
    @GetMapping("/searchDoctors")
    public CommonResponse searchDoctors(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("keyword") String keyword) {
        if (!redisTemplate.hasKey(token)) {
            return new CommonResponse(500, "未登录", null);
        }
        PageDto pageDto = new PageDto(page, size);
        return doctorService.searchDoctors(pageDto, keyword);
    }


}
