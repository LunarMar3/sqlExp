package org.simple.dbms.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDao {
    private String PatientID;
    private String Name;
    private String Gender;
    private String DateOfBirth;
    private String ContactNumber;
    private String Address;
    private String MedicalHistory;
    private String RoomID;
}
