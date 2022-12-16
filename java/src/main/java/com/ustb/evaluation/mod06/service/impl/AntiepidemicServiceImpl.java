package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustb.evaluation.mod06.VO.AntiepidemicVO;
import com.ustb.evaluation.mod06.mapper.AntiepidemicMapper;
import com.ustb.evaluation.mod06.mapper.StudentMapper;
import com.ustb.evaluation.mod06.pojo.Antiepidemic;
import com.ustb.evaluation.mod06.pojo.Student;
import com.ustb.evaluation.mod06.service.IAntiepidemicService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
@Service
public class AntiepidemicServiceImpl extends ServiceImpl<AntiepidemicMapper, Antiepidemic> implements IAntiepidemicService {
    @Resource
    private AntiepidemicMapper antiepidemicMapper;
    @Resource
    private StudentMapper studentMapper;
    @Override
    public List<AntiepidemicVO> findAll() {
        return antiepidemicMapper.selectAntiepidemic();
    }

    @Override
    public List<AntiepidemicVO> findPage(String search) {
        return antiepidemicMapper.searchAntiepidemic(search);
    }

    @Override
    public Integer add(AntiepidemicVO antiepidemicVO) {
        Student student = new Student();
        try {
            student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getFcode, antiepidemicVO.getFcode()));
        }catch (Exception e){
            return 0;
        }
        if (student!=studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getFname, antiepidemicVO.getFname()))){
            return 0;
        }
        Antiepidemic antiepidemic = new Antiepidemic();
        BeanUtils.copyProperties(antiepidemicVO,antiepidemic);
        antiepidemic.setFstudentId(student.getId());
        if (antiepidemic.getId() == null){
            return antiepidemicMapper.insert(antiepidemic);
        }
        return antiepidemicMapper.updateById(antiepidemic);
    }

    @Override
    public Integer del(Integer id) {
        return antiepidemicMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return antiepidemicMapper.deleteBatchIds(ids);
    }
}
