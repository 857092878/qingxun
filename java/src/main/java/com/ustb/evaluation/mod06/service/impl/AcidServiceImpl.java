package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustb.evaluation.mod06.VO.AcidVO;
import com.ustb.evaluation.mod06.mapper.AcidMapper;
import com.ustb.evaluation.mod06.mapper.StudentMapper;
import com.ustb.evaluation.mod06.pojo.Acid;
import com.ustb.evaluation.mod06.pojo.Student;
import com.ustb.evaluation.mod06.service.IAcidService;
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
public class AcidServiceImpl extends ServiceImpl<AcidMapper, Acid> implements IAcidService {

    @Resource
    private AcidMapper acidMapper;
    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<AcidVO> findAll() {
        return acidMapper.selectAcid();
    }

    @Override
    public List<AcidVO> findPage(String search) {
        return acidMapper.searchAcidVo(search);
    }

    @Override
    public Integer add(AcidVO acidVO) {
        Student student = new Student();
        try {
            student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getFcode, acidVO.getFcode()));
        }catch (Exception e){
            return 0;
        }
        if (student!=studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getFname, acidVO.getFname()))){
            return 0;
        }
        Acid acid = new Acid();
        BeanUtils.copyProperties(acidVO,acid);
        acid.setFstudentId(student.getId());
        if (acid.getId() == null){
            return acidMapper.insert(acid);
        }

        return acidMapper.updateById(acid);
    }

    @Override
    public Integer del(Integer id) {
        return acidMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return acidMapper.deleteBatchIds(ids);
    }
}
