package com.ustb.evaluation.mod06.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustb.evaluation.mod06.VO.AntiepidemicVO;
import com.ustb.evaluation.mod06.pojo.Antiepidemic;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
public interface IAntiepidemicService extends IService<Antiepidemic> {

    List<AntiepidemicVO> findAll();

    List<AntiepidemicVO> findPage(String search);


    Integer add(AntiepidemicVO antiepidemicVO);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
