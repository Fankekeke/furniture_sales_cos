package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RepairTypeInfo;
import cc.mrbird.febs.cos.service.IRepairTypeInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/repair-type-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairTypeInfoController {

    private final IRepairTypeInfoService repairTypeInfoService;

    /**
     * 分页获取积分维修保养信息
     *
     * @param page           分页对象
     * @param repairTypeInfo 积分维修保养信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RepairTypeInfo> page, RepairTypeInfo repairTypeInfo) {
        return R.ok(repairTypeInfoService.queryPage(page, repairTypeInfo));
    }

    /**
     * 获取ID获取积分维修保养详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(repairTypeInfoService.getById(id));
    }

    /**
     * 获取积分维修保养信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(repairTypeInfoService.list());
    }

    /**
     * 新增积分维修保养信息
     *
     * @param repairTypeInfo 积分维修保养信息
     * @return 结果
     */
    @PostMapping
    public R save(RepairTypeInfo repairTypeInfo) {
        repairTypeInfo.setCode("RP-" + System.currentTimeMillis());
        repairTypeInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(repairTypeInfoService.save(repairTypeInfo));
    }

    /**
     * 修改积分维修保养信息
     *
     * @param repairTypeInfo 积分维修保养信息
     * @return 结果
     */
    @PutMapping
    public R edit(RepairTypeInfo repairTypeInfo) {
        return R.ok(repairTypeInfoService.updateById(repairTypeInfo));
    }

    /**
     * 删除积分维修保养信息
     *
     * @param ids ids
     * @return 积分维修保养信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(repairTypeInfoService.removeByIds(ids));
    }
}
