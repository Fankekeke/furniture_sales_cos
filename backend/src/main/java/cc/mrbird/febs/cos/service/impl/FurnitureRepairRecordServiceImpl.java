package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.FurnitureRepairRecord;
import cc.mrbird.febs.cos.dao.FurnitureRepairRecordMapper;
import cc.mrbird.febs.cos.service.IFurnitureRepairRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class FurnitureRepairRecordServiceImpl extends ServiceImpl<FurnitureRepairRecordMapper, FurnitureRepairRecord> implements IFurnitureRepairRecordService {

    /**
     * 分页获取家具维修记录
     *
     * @param page                  分页对象
     * @param furnitureRepairRecord 家具维修记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<FurnitureRepairRecord> page, FurnitureRepairRecord furnitureRepairRecord) {
        return baseMapper.queryPage(page, furnitureRepairRecord);
    }
}
