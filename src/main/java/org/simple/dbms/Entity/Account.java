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
@TableName("accounts")
public class Account {
    @TableId(value = "AccountID", type = IdType.AUTO)
    private String AccountID;
    private String Username;
    private String Password;
    private String Role;
    @TableField("PatientID")
    private String PatientID;
    @TableField("DoctorID")
    private String DoctorID;
    @TableField("AdminID")
    private String AdminID;
}
