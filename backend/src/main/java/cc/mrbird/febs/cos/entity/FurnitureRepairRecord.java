package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 家具维修记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FurnitureRepairRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String code;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 所属商家
     */
    private Integer merchantId;

    /**
     * 状态（1.已下单 2.已派发 3.已完成）
     */
    private String status;

    /**
     * 上面维修地址
     */
    private Integer addressId;

    /**
     * 员工ID
     */
    private Integer staffId;

    /**
     * 家具种类名称
     */
    private String furnitureType;

    /**
     * 备注内容
     */
    private String content;

    /**
     * 维修保养类型
     */
    private Integer typeId;

    /**
     * 消耗积分
     */
    private BigDecimal integral;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 商家名称
     */
    @TableField(exist = false)
    private String merchantName;

}
