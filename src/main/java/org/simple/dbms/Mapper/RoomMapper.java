package org.simple.dbms.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.simple.dbms.Entity.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {

}
