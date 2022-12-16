package com.ustb.evaluation.mod06.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ustb.evaluation.mod06.VO.AccommodationVO;
import com.ustb.evaluation.mod06.pojo.Accommodation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
@Mapper
public interface AccommodationMapper extends BaseMapper<Accommodation> {

    List<AccommodationVO> selectAccondation();

    List<AccommodationVO> searchAccondation(String search);

}
