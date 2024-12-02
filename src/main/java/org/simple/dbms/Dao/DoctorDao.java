package org.simple.dbms.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDao {
    private String DoctorID;
    private String Name;
    private String Gender;
    private Integer DepartmentID;
    private String Email;
    private Integer Verified;
    private String ContactNumber;
}
