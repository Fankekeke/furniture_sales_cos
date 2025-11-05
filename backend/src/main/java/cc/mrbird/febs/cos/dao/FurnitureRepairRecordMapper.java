package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.FurnitureRepairRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface FurnitureRepairRecordMapper extends BaseMapper<FurnitureRepairRecord> {

    /**
     * 分页获取家具维修记录
     *
     * @param page                  分页对象
     * @param furnitureRepairRecord 家具维修记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<FurnitureRepairRecord> page, @Param("furnitureRepairRecord") FurnitureRepairRecord furnitureRepairRecord);
}
