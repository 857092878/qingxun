package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ustb.evaluation.mod06.pojo.Classrole;
import com.ustb.evaluation.mod06.mapper.ClassroleMapper;
import com.ustb.evaluation.mod06.service.IClassroleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-14
 */
@Service
public class ClassroleServiceImpl extends ServiceImpl<ClassroleMapper, Classrole> implements IClassroleService {

    @Resource
    private ClassroleMapper classroleMapper;

    @Override
    public List<Classrole> findPage(String search) {
        LambdaQueryWrapper<Classrole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Classrole::getClassRoleName,search);
        return classroleMapper.selectList(queryWrapper);
    }

    @Override
    public List<Classrole> findAll() {
        List<Classrole> classroles = classroleMapper.selectList(null);
        System.out.println(classroles);
        return classroles;
    }

    @Override
    public Integer add(Classrole classrole) {
        if (classrole.getFclassRoleId() == null){
            return classroleMapper.insert(classrole);
        }
        return classroleMapper.updateById(classrole);
    }

    @Override
    public Integer del(Integer id) {
        return classroleMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return classroleMapper.deleteBatchIds(ids);
    }
}
