package org.simple.dbms.Service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.simple.dbms.Dao.AccountDao;
import org.simple.dbms.Entity.Account;
import org.simple.dbms.Entity.Doctor;
import org.simple.dbms.Mapper.AccountMapper;
import org.simple.dbms.Mapper.DoctorMapper;
import org.simple.dbms.Util.CommonResponse;
import org.simple.dbms.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private JwtUtil jwtUtil;


    public CommonResponse login(AccountDao accountDao) {
        String username = accountDao.getUsername();
        String password = accountDao.getPassword();
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Username", username);
        String hashPassword = SecureUtil.md5(password);
        queryWrapper.eq("Password", hashPassword);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (accounts.isEmpty()){
            return new CommonResponse(500, "用户不存在", null);
        }
        Account account = accounts.get(0);
        if (account.getRole().equals("Doctor")){
            Doctor doctor = doctorMapper.selectById(account.getDoctorID());
            if (doctor.getVerified() == 0){
                return new CommonResponse(500, "用户未通过审核", null);
            }
        }
        String token = jwtUtil.generateToken(account);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("role", account.getRole());
        return new CommonResponse(200, "登录成功", map);

    }
    public CommonResponse register(AccountDao accountDao) {
        String username = accountDao.getUsername();
        String password = accountDao.getPassword();
        String role = accountDao.getRole();
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Username", username);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (!accounts.isEmpty()){
            return new CommonResponse(500, "用户已存在", null);
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(SecureUtil.md5(password));
        account.setRole(role);
        accountMapper.insert(account);
        return new CommonResponse(200, "注册成功", account);
    }
}
