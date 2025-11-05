package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RepairTypeInfo;
import cc.mrbird.febs.cos.dao.RepairTypeInfoMapper;
import cc.mrbird.febs.cos.service.IRepairTypeInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class RepairTypeInfoServiceImpl extends ServiceImpl<RepairTypeInfoMapper, RepairTypeInfo> implements IRepairTypeInfoService {

    /**
     * 分页获取积分维修保养信息
     *
     * @param page           分页对象
     * @param repairTypeInfo 积分维修保养信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<RepairTypeInfo> page, RepairTypeInfo repairTypeInfo) {
        return baseMapper.queryPage(page, repairTypeInfo);
    }
}
