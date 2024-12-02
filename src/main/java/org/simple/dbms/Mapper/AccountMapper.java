package org.simple.dbms.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.simple.dbms.Entity.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
