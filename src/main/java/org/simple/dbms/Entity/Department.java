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
@TableName("departments")
public class Department {
    @TableId(value = "DepartmentID", type = IdType.AUTO)
    private String DepartmentID;
    @TableField("DepartmentName")
    private String DepartmentName;
}
