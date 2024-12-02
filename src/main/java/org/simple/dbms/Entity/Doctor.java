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
@TableName("doctors")
public class Doctor {
    @TableId(value = "DoctorID", type = IdType.AUTO)
    private String DoctorID;
    private String Name;
    private String Gender;
    @TableField("ContactNumber")
    private String ContactNumber;
    @TableField("DepartmentID")
    private Integer DepartmentID;
    private String Email;
    private Integer Verified;
}
