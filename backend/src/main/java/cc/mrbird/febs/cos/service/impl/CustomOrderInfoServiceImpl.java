package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CustomOrderInfo;
import cc.mrbird.febs.cos.dao.CustomOrderInfoMapper;
import cc.mrbird.febs.cos.service.ICustomOrderInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class CustomOrderInfoServiceImpl extends ServiceImpl<CustomOrderInfoMapper, CustomOrderInfo> implements ICustomOrderInfoService {

    /**
     * 分页获取家具定制信息
     *
     * @param page            分页对象
     * @param customOrderInfo 家具定制信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryCustomPage(Page<CustomOrderInfo> page, CustomOrderInfo customOrderInfo) {
        return baseMapper.queryCustomPage(page, customOrderInfo);
    }
}
