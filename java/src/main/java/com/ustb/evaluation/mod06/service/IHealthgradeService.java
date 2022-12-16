package com.ustb.evaluation.mod06.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustb.evaluation.mod06.pojo.Healthgrade;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
public interface IHealthgradeService extends IService<Healthgrade> {

    List<Healthgrade> findPage(String search);

    Integer add(Healthgrade healthgrade);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
