package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.CustomOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface CustomOrderInfoMapper extends BaseMapper<CustomOrderInfo> {

    /**
     * 分页获取家具定制信息
     *
     * @param page            分页对象
     * @param customOrderInfo 家具定制信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryCustomPage(Page<CustomOrderInfo> page, @Param("customOrderInfo") CustomOrderInfo customOrderInfo);
}
