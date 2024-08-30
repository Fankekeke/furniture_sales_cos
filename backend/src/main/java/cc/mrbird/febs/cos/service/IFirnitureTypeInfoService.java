package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.FirnitureTypeInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IFirnitureTypeInfoService extends IService<FirnitureTypeInfo> {

    /**
     * 分页获取家具类型信息
     *
     * @param page              分页对象
     * @param firnitureTypeInfo 家具类型信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectFirnitureTypePage(Page<FirnitureTypeInfo> page, FirnitureTypeInfo firnitureTypeInfo);
}
