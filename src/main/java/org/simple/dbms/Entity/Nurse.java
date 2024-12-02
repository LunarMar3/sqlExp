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
@TableName("nurses")
public class Nurse {
    @TableId(value = "NurseID", type = IdType.AUTO)
    private String NurseID;
    private String Name;
    private String Gender;
    @TableField("ContactNumber")
    private String ContactNumber;
    @TableField("AssignedRoomID")
    private String AssignedRoomID;
}
