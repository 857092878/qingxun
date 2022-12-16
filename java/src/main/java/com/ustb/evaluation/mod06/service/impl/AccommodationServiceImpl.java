package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustb.evaluation.mod06.VO.AccommodationVO;
import com.ustb.evaluation.mod06.ex.RunException;
import com.ustb.evaluation.mod06.mapper.AccommodationMapper;
import com.ustb.evaluation.mod06.mapper.StudentMapper;
import com.ustb.evaluation.mod06.pojo.Accommodation;
import com.ustb.evaluation.mod06.pojo.Student;
import com.ustb.evaluation.mod06.service.IAccommodationService;
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
public class AccommodationServiceImpl extends ServiceImpl<AccommodationMapper, Accommodation> implements IAccommodationService {

    @Resource
    private AccommodationMapper accommodationMapper;
    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<AccommodationVO> findAll() {
        return accommodationMapper.selectAccondation();
    }

    @Override
    public List<AccommodationVO> findPage(String search) {
        return accommodationMapper.searchAccondation(search);
    }

    @Override
    public Integer add(AccommodationVO accommodationVO) {
        Student student = new Student();
        try {
            student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getFcode, accommodationVO.getFcode()));
        }catch (Exception e){
            throw new RunException("系统内部错误");
//            return 0;
        }
        if (student!=studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getFname, accommodationVO.getFname()))){
            throw new RunException("输入有误，学号和姓名不匹配");
//            return 0;
        }
        Accommodation accommodation = new Accommodation();
        BeanUtils.copyProperties(accommodationVO,accommodation);
        accommodation.setFstudentId(student.getId());

        if (accommodation.getId() == null){
            //添加
            return accommodationMapper.insert(accommodation);
        }
        //编辑
        return accommodationMapper.updateById(accommodation);
    }

    @Override
    public Integer del(Integer id) {
        return accommodationMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return accommodationMapper.deleteBatchIds(ids);
    }

}
