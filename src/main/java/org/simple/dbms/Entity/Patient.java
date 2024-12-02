package org.simple.dbms.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("patients")
public class Patient {
    @TableId(value = "PatientID", type = IdType.AUTO)
    private String PatientID;
    private String Name;
    private String Gender;
    @TableField("DateOfBirth")
    private String DateOfBirth;
    @TableField("ContactNumber")
    private String ContactNumber;
    private String Address;
    @TableField("MedicalHistory")
    private String MedicalHistory;
    @TableField("RoomID")
    private String RoomID;
}
