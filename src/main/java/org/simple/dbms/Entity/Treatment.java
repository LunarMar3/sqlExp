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
@TableName("treatments")
public class Treatment {
    @TableId(value = "TreatmentID", type = IdType.AUTO)
    private String TreatmentID;
    @TableField("PatientID")
    private String PatientID;
    @TableField("DoctorID")
    private String DoctorID;
    @TableField("TreatmentDate")
    private String TreatmentDate;
    @TableField("TreatmentDetails")
    private String TreatmentDetails;
    private String Prescription;
}
