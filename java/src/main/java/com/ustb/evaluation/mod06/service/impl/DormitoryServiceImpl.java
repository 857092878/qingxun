package com.ustb.evaluation.mod06.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustb.evaluation.mod06.mapper.DormitoryMapper;
import com.ustb.evaluation.mod06.pojo.Dormitory;
import com.ustb.evaluation.mod06.service.IDormitoryService;
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
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements IDormitoryService {
    @Resource
    private DormitoryMapper dormitoryMapper;

    @Override
    public List<Dormitory> findPage(String search) {
        LambdaQueryWrapper<Dormitory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Dormitory::getFname,search);
        return dormitoryMapper.selectList(queryWrapper);
    }

    @Override
    public Integer add(Dormitory dormitory) {
        if (dormitory.getId() == null){
            return dormitoryMapper.insert(dormitory);
        }
        return dormitoryMapper.updateById(dormitory);
    }

    @Override
    public Integer del(Integer id) {
        return dormitoryMapper.deleteById(id);
    }

    @Override
    public Integer dels(List<Integer> ids) {
        return dormitoryMapper.deleteBatchIds(ids);
    }
}
