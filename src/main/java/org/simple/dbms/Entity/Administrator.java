package org.simple.dbms.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("administrators")
public class Administrator {
    @TableId(value = "AdministratorID", type = IdType.AUTO)
    private String AdminID;
    private String Username;
    private String Password;
}
