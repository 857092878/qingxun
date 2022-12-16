package com.ustb.evaluation.mod06.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustb.evaluation.mod06.pojo.Politics;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
public interface IPoliticsService extends IService<Politics> {

    List<Politics> findPage(String search);

    Integer add(Politics politics);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
