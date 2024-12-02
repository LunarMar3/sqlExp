package org.simple.dbms.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simple.dbms.Dao.PageDto;
import org.simple.dbms.Entity.Nurse;
import org.simple.dbms.Mapper.NurseMapper;
import org.simple.dbms.Util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseService {
    @Autowired
    private NurseMapper nurseMapper;

    public CommonResponse getNurseList(PageDto pageDto) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Nurse> page1 = new Page<>(page, size);
        return new CommonResponse(200, "获取成功", nurseMapper.selectPage(page1, null));
    }

    public CommonResponse updateNurse(Nurse nurse) {
        nurseMapper.updateById(nurse);
        return new CommonResponse(200, "更新成功", null);
    }

    public CommonResponse deleteNurse(int nurseID) {
        nurseMapper.deleteById(nurseID);
        return new CommonResponse(200, "删除成功", null);
    }

    public CommonResponse insertNurse(Nurse nurse) {
        nurseMapper.insert(nurse);
        return new CommonResponse(200, "插入成功", null);
    }
    public CommonResponse search(PageDto pageDto, String keyword) {
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        Page<Nurse> page1 = new Page<>(page, size);
        QueryWrapper<Nurse> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("Name", keyword)
                .or()
                .like("AssignedRoomID", keyword);
        return new CommonResponse(200, "获取成功", nurseMapper.selectPage(page1, queryWrapper));
    }
}
