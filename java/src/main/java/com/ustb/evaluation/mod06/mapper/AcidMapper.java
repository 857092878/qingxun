package com.ustb.evaluation.mod06.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ustb.evaluation.mod06.VO.AcidVO;
import com.ustb.evaluation.mod06.pojo.Acid;
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
public interface AcidMapper extends BaseMapper<Acid> {

    List<AcidVO> selectAcid();

    List<AcidVO> searchAcidVo(String search);

}
