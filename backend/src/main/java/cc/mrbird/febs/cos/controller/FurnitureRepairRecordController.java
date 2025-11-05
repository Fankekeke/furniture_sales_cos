package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/furniture-repair-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FurnitureRepairRecordController {

    private final IFurnitureRepairRecordService furnitureRepairRecordService;

    private final IUserInfoService userInfoService;

    private final IOrderInfoService orderInfoService;

    private final IOrderItemInfoService orderItemInfoService;

    private final IDishesInfoService dishesInfoService;

    private final IRepairTypeInfoService repairTypeInfoService;


    /**
     * 分页获取家具维修记录
     *
     * @param page                  分页对象
     * @param furnitureRepairRecord 家具维修记录
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<FurnitureRepairRecord> page, FurnitureRepairRecord furnitureRepairRecord) {
        return R.ok(furnitureRepairRecordService.queryPage(page, furnitureRepairRecord));
    }

    /**
     * 获取用户历史订单
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/queryOrderHistory/{userId}")
    public R queryOrderHistory(@PathVariable("userId") Integer userId) throws FebsException {
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        if (userInfo == null) {
            throw new FebsException("用户不存在");
        }
        // 返回数据
        List<DishesInfo> result = new ArrayList<>();
        // 获取用户订单
        List<Integer> orderIds = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getUserId, userId).eq(OrderInfo::getStatus, "3")).stream().map(OrderInfo::getId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(orderIds)) {
            return R.ok(result);
        }
        // 获取订单详情
        List<Integer> furnitureIdList = orderItemInfoService.list(Wrappers.<OrderItemInfo>lambdaQuery().in(OrderItemInfo::getOrderId, orderIds)).stream().map(OrderItemInfo::getDishesId).distinct().collect(Collectors.toList());
        if (CollectionUtil.isEmpty(furnitureIdList)) {
            return R.ok(result);
        }
        result = dishesInfoService.list(Wrappers.<DishesInfo>lambdaQuery().in(DishesInfo::getId, furnitureIdList));
        return R.ok(result);
    }

    /**
     * 获取ID获取详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(furnitureRepairRecordService.getById(id));
    }

    /**
     * 获取家具维修记录列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(furnitureRepairRecordService.list());
    }

    /**
     * 新增家具维修记录
     *
     * @param furnitureRepairRecord 家具维修记录
     * @return 结果
     */
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public R save(FurnitureRepairRecord furnitureRepairRecord) throws FebsException {
        // 获取所属用户
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, furnitureRepairRecord.getUserId()));
        RepairTypeInfo repairTypeInfo = repairTypeInfoService.getById(furnitureRepairRecord.getTypeId());

        if (repairTypeInfo.getIntegral().compareTo(userInfo.getIntegral()) > 0) {
            throw new FebsException("用户积分为【" + userInfo.getIntegral() + "】 无法兑换");
        }
        furnitureRepairRecord.setUserId(userInfo.getId());
        ;

        // 添加物品销量
        repairTypeInfo.setSaleNum(repairTypeInfo.getSaleNum() == null ? 0 : repairTypeInfo.getSaleNum() + 1);
        repairTypeInfoService.updateById(repairTypeInfo);

        // 更新用户积分
        userInfo.setIntegral(NumberUtil.sub(userInfo.getIntegral(), repairTypeInfo.getIntegral()));
        userInfoService.updateById(userInfo);

        furnitureRepairRecord.setCode("FR-" + System.currentTimeMillis());
        furnitureRepairRecord.setStatus("1");
        furnitureRepairRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(furnitureRepairRecordService.save(furnitureRepairRecord));
    }

    /**
     * 分配维修人员
     *
     * @param repairId 维修ID
     * @param staffId  员工ID
     * @return 结果
     */
    @PutMapping("/repairDistribute")
    public R repairDistribute(Integer repairId, Integer staffId) {
        FurnitureRepairRecord furnitureRepairRecord = furnitureRepairRecordService.getById(repairId);
        furnitureRepairRecord.setStatus("2");
        furnitureRepairRecord.setStaffId(staffId);
        return R.ok(furnitureRepairRecordService.updateById(furnitureRepairRecord));
    }

    /**
     * 修改家具维修记录
     *
     * @param furnitureRepairRecord 家具维修记录
     * @return 结果
     */
    @PutMapping
    public R edit(FurnitureRepairRecord furnitureRepairRecord) {
        return R.ok(furnitureRepairRecordService.updateById(furnitureRepairRecord));
    }

    /**
     * 删除家具维修记录
     *
     * @param ids ids
     * @return 家具维修记录
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(furnitureRepairRecordService.removeByIds(ids));
    }
}
