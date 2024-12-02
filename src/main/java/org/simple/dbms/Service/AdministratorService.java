package org.simple.dbms.Service;

import org.simple.dbms.Entity.Account;
import org.simple.dbms.Entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.simple.dbms.Mapper.AdministratorMapper;
@Service
public class AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    public String insert(Account account) {
        Administrator administrator = new Administrator();
        administrator.setUsername(account.getUsername());
        administrator.setPassword(account.getPassword());
        administratorMapper.insert(administrator);
        return administrator.getAdminID();
    }
}
