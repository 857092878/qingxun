package com.ustb.evaluation.mod06.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustb.evaluation.mod06.VO.StudentVO;
import com.ustb.evaluation.mod06.pojo.Student;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
public interface IStudentService extends IService<Student> {

    List<StudentVO> findAll();

    List<StudentVO> findPage(String search);

    Integer add(StudentVO studentVO);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
