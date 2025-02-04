package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CustomOrderInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.ICustomOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/custom-order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomOrderInfoController {

    private final IUserInfoService userInfoService;

    private final ICustomOrderInfoService customOrderInfoService;

    /**
     * 分页获取家具定制信息
     *
     * @param page            分页对象
     * @param customOrderInfo 家具定制信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CustomOrderInfo> page, CustomOrderInfo customOrderInfo) {
        return R.ok(customOrderInfoService.queryCustomPage(page, customOrderInfo));
    }

    /**
     * 获取ID获取家具定制详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(customOrderInfoService.getById(id));
    }

    /**
     * 获取家具定制信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(customOrderInfoService.list());
    }

    /**
     * 根据用户获取家具定制
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/listByUserId/{userId}")
    public R listByUserId(@PathVariable("userId") Integer userId) {
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        if (userInfo == null) {
            return R.ok(Collections.emptyList());
        }
        return R.ok(customOrderInfoService.list(Wrappers.<CustomOrderInfo>lambdaQuery().eq(CustomOrderInfo::getUserId, userInfo.getId())));
    }

    /**
     * 新增家具定制信息
     *
     * @param customOrderInfo 家具定制信息
     * @return 结果
     */
    @PostMapping
    public R save(CustomOrderInfo customOrderInfo) {
        // 获取所属用户
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, customOrderInfo.getUserId()));
        if (userInfo != null) {
            customOrderInfo.setUserId(userInfo.getId());
        }
        customOrderInfo.setStatus("0");
        customOrderInfo.setCode("CUS-" + System.currentTimeMillis());
        customOrderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(customOrderInfoService.save(customOrderInfo));
    }

    /**
     * 修改家具定制信息
     *
     * @param customOrderInfo 家具定制信息
     * @return 结果
     */
    @PutMapping
    public R edit(CustomOrderInfo customOrderInfo) {
        return R.ok(customOrderInfoService.updateById(customOrderInfo));
    }

    /**
     * 删除家具定制信息
     *
     * @param ids ids
     * @return 家具定制信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(customOrderInfoService.removeByIds(ids));
    }
}
