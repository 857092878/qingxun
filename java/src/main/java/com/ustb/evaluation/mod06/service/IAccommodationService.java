package com.ustb.evaluation.mod06.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustb.evaluation.mod06.VO.AccommodationVO;
import com.ustb.evaluation.mod06.pojo.Accommodation;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
public interface IAccommodationService extends IService<Accommodation> {

    List<AccommodationVO> findAll();

    List<AccommodationVO> findPage(String search);

    Integer add(AccommodationVO accommodationVO);

    Integer del(Integer id);

    Integer dels(List<Integer> ids);
}
