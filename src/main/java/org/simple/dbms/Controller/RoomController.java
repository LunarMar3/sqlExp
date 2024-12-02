package org.simple.dbms.Controller;

import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Room;
import org.simple.dbms.Service.RoomService;
import org.simple.dbms.Util.CommonResponse;
import org.simple.dbms.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getRoomList")
    public CommonResponse getRoomList(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return roomService.getRoomList(pageDto);
    }

    @PostMapping("/updateRoom")
    public CommonResponse updateRoom(@RequestHeader("Authorization") String token, @RequestBody Room room) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return roomService.updateRoom(room);
    }
    @PostMapping("/insertRoom")
    public CommonResponse insertRoom(@RequestHeader("Authorization") String token, @RequestBody Room room) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return roomService.insertRoom(room);
    }
    @GetMapping("/deleteRoom")
    public CommonResponse deleteRoom(@RequestHeader("Authorization") String token, @RequestParam("roomID") int roomID) {
        String role = jwtUtil.getRoleFromToken(token);
        if (!role.equals("Administrator")) {
            return new CommonResponse(500, "无权限", null);
        }
        return roomService.deleteRoom(roomID);
    }
    @GetMapping("/searchRooms")
    public CommonResponse search(@RequestHeader("Authorization") String token, @RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("keyword") String keyword) {
        PageDto pageDto = new PageDto(page, size);
        String role = jwtUtil.getRoleFromToken(token);
        if (!(role.equals("Administrator")||role.equals("Doctor"))) {
            return new CommonResponse(500, "无权限", null);
        }
        return roomService.search(pageDto, keyword);
    }
}
