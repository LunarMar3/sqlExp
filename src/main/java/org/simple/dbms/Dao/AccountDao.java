package org.simple.dbms.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDao {
    private String username;
    private String password;
    private String role;

    private String Name;
    private String Gender;
    private String ContactNumber;

    private Integer DepartmentID;
    private String Email;
    private Integer Verified;

    private String DateOfBirth;
    private String Address;
    private String MedicalHistory;
    private String RoomID;
}
