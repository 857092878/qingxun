package com.ustb.evaluation.mod06.service;

import com.github.pagehelper.PageInfo;
import com.ustb.evaluation.mod06.dto.PageDto;
import com.ustb.evaluation.mod06.pojo.Classrole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-14
 */
public interface IClassroleService extends IService<Classrole> {


    List<Classrole> findPage(String search);

    List<Classrole> findAll();

    Integer add(Classrole classrole);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
