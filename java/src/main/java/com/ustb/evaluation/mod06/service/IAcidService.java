package com.ustb.evaluation.mod06.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustb.evaluation.mod06.VO.AcidVO;
import com.ustb.evaluation.mod06.pojo.Acid;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
public interface IAcidService extends IService<Acid> {

    List<AcidVO> findAll();

    List<AcidVO> findPage(String search);

    Integer add(AcidVO acidVO);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
