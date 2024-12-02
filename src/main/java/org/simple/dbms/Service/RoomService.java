package org.simple.dbms.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Room;
import org.simple.dbms.Mapper.RoomMapper;
import org.simple.dbms.Util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private RoomMapper roomMapper;


    public CommonResponse getRoomList(PageDto pageDto) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Room> page1 = new Page<>(page, size);
        return new CommonResponse(200, "获取成功", roomMapper.selectPage(page1, null));
    }

    public CommonResponse updateRoom(Room room) {
        roomMapper.updateById(room);
        return new CommonResponse(200, "更新成功", null);
    }

    public CommonResponse insertRoom(Room room) {
        roomMapper.insert(room);
        return new CommonResponse(200, "插入成功", null);
    }

    public CommonResponse deleteRoom(int roomID) {
        roomMapper.deleteById(roomID);
        return new CommonResponse(200, "删除成功", null);
    }

    public CommonResponse search(PageDto pageDto, String keyword) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Room> page1 = new Page<>(page, size);
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("RoomID", keyword)
                .or()
                .like("RoomType", keyword)
                .or()
                .like("RoomNumber", keyword);
        return new CommonResponse(200, "获取成功", roomMapper.selectPage(page1, queryWrapper));
    }
}
