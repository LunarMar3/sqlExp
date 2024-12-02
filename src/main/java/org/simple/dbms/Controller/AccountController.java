package org.simple.dbms.Controller;
import org.simple.dbms.Dao.AccountDao;
import org.simple.dbms.Entity.Account;
import org.simple.dbms.Mapper.AccountMapper;
import org.simple.dbms.Service.AccountService;
import org.simple.dbms.Service.AdministratorService;
import org.simple.dbms.Service.DoctorService;
import org.simple.dbms.Util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.simple.dbms.Service.PatientService;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AdministratorService administratorService;

    @PostMapping("/login")
    public CommonResponse login(@RequestBody AccountDao accountDao) {
        return accountService.login(accountDao);
    }
    
    @PostMapping("/register")
    public CommonResponse register(@RequestBody AccountDao accountDao) {
        CommonResponse commonResponse = accountService.register(accountDao);
        if (commonResponse.getCode() == 200) {
            String role = accountDao.getRole();
            Account account = (Account) commonResponse.getData();
            switch (role) {
                case "Administrator":
                    String AdminID = administratorService.insert(account);
                    account.setAdminID(AdminID);
                    break;
                case "Doctor":
                    String DoctorID = doctorService.insert(accountDao);
                    account.setDoctorID(DoctorID);
                    break;
                case "Patient":
                     String PatientID = patientService.insert(accountDao);
                     account.setPatientID(PatientID);
                    break;
                default:
                    return new CommonResponse(500, "注册失败", null);
            }
            accountMapper.updateById(account);
            return new CommonResponse(200, "注册成功", null);
        } else {
            return new CommonResponse(500, "注册失败", null);
        }
    }
}
