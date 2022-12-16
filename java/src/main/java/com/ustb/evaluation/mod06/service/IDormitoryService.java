package com.ustb.evaluation.mod06.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustb.evaluation.mod06.pojo.Dormitory;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
public interface IDormitoryService extends IService<Dormitory> {

    List<Dormitory> findPage(String search);

    Integer add(Dormitory dormitory);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
