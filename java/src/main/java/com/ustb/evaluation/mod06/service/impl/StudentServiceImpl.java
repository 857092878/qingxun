package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustb.evaluation.mod06.VO.StudentVO;
import com.ustb.evaluation.mod06.mapper.ClassroleMapper;
import com.ustb.evaluation.mod06.mapper.NationalityMapper;
import com.ustb.evaluation.mod06.mapper.PoliticsMapper;
import com.ustb.evaluation.mod06.mapper.StudentMapper;
import com.ustb.evaluation.mod06.pojo.Student;
import com.ustb.evaluation.mod06.service.IStudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private PoliticsMapper politicsMapper;
    @Resource
    private ClassroleMapper classroleMapper;
    @Resource
    private NationalityMapper nationalityMapper;

    @Override
    public List<StudentVO> findAll() {
        return studentMapper.selectStudent();
    }

    @Override
    public List<StudentVO> findPage(String search) {
        return studentMapper.searchStudent(search);
    }

    @Override
    public Integer add(StudentVO studentVO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentVO,student);

        if (student.getId() == null){
            return studentMapper.insert(student);
        }
        return studentMapper.updateById(student);
    }

    @Override
    public Integer del(Integer id) {
        return studentMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return studentMapper.deleteBatchIds(ids);
    }
}
