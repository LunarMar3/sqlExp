package org.simple.dbms.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rooms")
public class Room {
    @TableId(value = "RoomID", type = IdType.AUTO)
    private String RoomID;
    @TableField("RoomNumber")
    private String RoomNumber;
    @TableField("RoomType")
    private String RoomType;
    private String Capacity;
    @TableField("OccupiedBeds")
    private String OccupiedBeds;
}
