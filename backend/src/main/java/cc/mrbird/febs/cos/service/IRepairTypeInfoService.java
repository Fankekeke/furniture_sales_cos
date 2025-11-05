package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RepairTypeInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IRepairTypeInfoService extends IService<RepairTypeInfo> {

    /**
     * 分页获取积分维修保养信息
     *
     * @param page           分页对象
     * @param repairTypeInfo 积分维修保养信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<RepairTypeInfo> page, RepairTypeInfo repairTypeInfo);
}
