package org.simple.dbms.Controller;

import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Treatment;
import org.simple.dbms.Service.TreatmentService;
import org.simple.dbms.Util.CommonResponse;
import org.simple.dbms.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/treatment")
public class TreatmentController {
    @Autowired
    private TreatmentService treatmentService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getTreatmentList")
    public CommonResponse getTreatmentList(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return treatmentService.getTreatmentList(pageDto);
    }

    @GetMapping("/getTreatment")
    public CommonResponse getTreatment(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        String role = jwtUtil.getRoleFromToken(token);
        PageDto pageDto = new PageDto(page, size);

        if (role.equals("Doctor")) {
            String doctorID = jwtUtil.getDoctorID(token);
            return treatmentService.getTreatment(pageDto,doctorID);
        }
        if (role.equals("Patient")) {
            String patientID = jwtUtil.getPaitentID(token);
            return treatmentService.getTreatmentPa(pageDto,patientID);
        }
        return new CommonResponse(500, "无权限", null);
    }
    @PostMapping("/updateTreatment")
    public CommonResponse updateTreatment(@RequestHeader("Authorization") String token, @RequestBody Treatment treatment) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        if (role.equals("Doctor")) {
            treatment.setDoctorID(jwtUtil.getDoctorID(token));
        }
        return treatmentService.updateTreatment(treatment);
    }

    @GetMapping("/deleteTreatment")
    public CommonResponse deleteTreatment(@RequestHeader("Authorization") String token, @RequestParam("treatmentID") int treatmentID) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return treatmentService.deleteTreatment(treatmentID);
    }

    @PostMapping("/insertTreatment")
    public CommonResponse insertTreatment(@RequestHeader("Authorization") String token, @RequestBody Treatment treatment) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        if (role.equals("Doctor")) {
            treatment.setDoctorID(jwtUtil.getDoctorID(token));
        }
        return treatmentService.insertTreatment(treatment);
    }
    @PostMapping("/searchTreatments")
    public CommonResponse search(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("keyword") String keyword) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return treatmentService.search(pageDto, keyword);
    }
}
