package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustb.evaluation.mod06.mapper.PoliticsMapper;
import com.ustb.evaluation.mod06.pojo.Politics;
import com.ustb.evaluation.mod06.service.IPoliticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
@Service
public class PoliticsServiceImpl extends ServiceImpl<PoliticsMapper, Politics> implements IPoliticsService {
    @Resource
    private PoliticsMapper politicsMapper;

    @Override
    public List<Politics> findPage(String search) {
        LambdaQueryWrapper<Politics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Politics::getFname,search);

        return politicsMapper.selectList(queryWrapper);

    }

    @Override
    public Integer add(Politics politics) {
        if (politics.getFcode() == null){
            return politicsMapper.insert(politics);
        }
        return politicsMapper.updateById(politics);
    }

    @Override
    public Integer del(Integer id) {
        return politicsMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return politicsMapper.deleteBatchIds(ids);
    }
}
