package com.ustb.evaluation.mod06.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ustb.evaluation.mod06.VO.AntiepidemicVO;
import com.ustb.evaluation.mod06.pojo.Antiepidemic;
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
public interface AntiepidemicMapper extends BaseMapper<Antiepidemic> {

    List<AntiepidemicVO> selectAntiepidemic();

    List<AntiepidemicVO> searchAntiepidemic(String search);

}
