package org.simple.dbms.Controller;

import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Dao.PatientDao;
import org.simple.dbms.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.simple.dbms.Service.PatientService;
import org.simple.dbms.Util.CommonResponse;

@RequestMapping("/patient")
@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getPatientList")
    public CommonResponse getPatientList(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator") || role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return patientService.getPatientList(pageDto);
    }

    @GetMapping("/getSelf")
    public CommonResponse getSelf(@RequestHeader("Authorization") String token) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Patient")) {
            return new CommonResponse(500, "无权限", null);
        }
        return patientService.getPatient(jwtUtil.getPaitentID(token));
    }

    @PostMapping("/updatePatient")
    public CommonResponse updatePatient(@RequestHeader("Authorization") String token, @RequestBody PatientDao patientDao) {
        String role = jwtUtil.getRoleFromToken(token);
        if (role.equals("Administrator")) {
            patientService.updatePatient(patientDao);
        } else if (role.equals("Patient")) {
            PatientDao newPatientDao = new PatientDao();
            newPatientDao.setPatientID(jwtUtil.getPaitentID(token));
            newPatientDao.setName(patientDao.getName());
            newPatientDao.setGender(patientDao.getGender());
            newPatientDao.setContactNumber(patientDao.getContactNumber());
            newPatientDao.setAddress(patientDao.getAddress());
            newPatientDao.setDateOfBirth(patientDao.getDateOfBirth());
            patientService.updatePatient(newPatientDao);
        } else if (role.equals("Doctor")) {
            PatientDao newPatientDao = new PatientDao();
            newPatientDao.setPatientID(patientDao.getPatientID());
            newPatientDao.setRoomID(patientDao.getRoomID());
            newPatientDao.setMedicalHistory(patientDao.getMedicalHistory());
            patientService.updatePatient(newPatientDao);
        } else {
            return new CommonResponse(500, "无权限", null);
        }
        return new CommonResponse(200, "更新成功", null);
    }

    @GetMapping("/deletePatient")
    public CommonResponse deletePatient(@RequestHeader("Authorization") String token,@RequestParam String patientID) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        patientService.deletePatient(patientID);
        return new CommonResponse(200, "删除成功", null);
    }
    @GetMapping("/searchPatients")
    public CommonResponse search(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("keyword") String keyword) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator") || role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return patientService.searchPatients(pageDto, keyword);
    }
}
