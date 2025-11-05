package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.FurnitureRepairRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IFurnitureRepairRecordService extends IService<FurnitureRepairRecord> {

    /**
     * 分页获取家具维修记录
     *
     * @param page                  分页对象
     * @param furnitureRepairRecord 家具维修记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<FurnitureRepairRecord> page, FurnitureRepairRecord furnitureRepairRecord);
}
