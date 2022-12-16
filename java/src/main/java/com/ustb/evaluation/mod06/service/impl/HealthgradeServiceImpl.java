package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustb.evaluation.mod06.mapper.HealthgradeMapper;
import com.ustb.evaluation.mod06.pojo.Healthgrade;
import com.ustb.evaluation.mod06.service.IHealthgradeService;
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
public class HealthgradeServiceImpl extends ServiceImpl<HealthgradeMapper, Healthgrade> implements IHealthgradeService {
    @Resource
    private HealthgradeMapper healthgradeMapper;

    @Override
    public List<Healthgrade> findPage(String search) {
        LambdaQueryWrapper<Healthgrade> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Healthgrade::getFhealth,search);
        return healthgradeMapper.selectList(queryWrapper);

    }

    @Override
    public Integer add(Healthgrade healthgrade) {
        if (healthgrade.getFhealthId() == null){
            return healthgradeMapper.insert(healthgrade);
        }
        return healthgradeMapper.updateById(healthgrade);
    }

    @Override
    public Integer del(Integer id) {
        return healthgradeMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return healthgradeMapper.deleteBatchIds(ids);
    }
}
