package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 定制内容
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CustomOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 定制编号
     */
    private String code;

    /**
     * 所属商家
     */
    private Integer merchantId;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 定制要求
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 状态（0.未接单 1.已接单 2.定制完成 3.已付款 4.已收货 5.驳回）
     */
    private String status;

    /**
     * 定制价格
     */
    private BigDecimal orderPrice;

    /**
     * 接单备注
     */
    private String remark;

    /**
     * 配送员
     */
    private Integer staffId;
    private String type;
    private String size;
    private String model;
    private String color;
    private String checkDate;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String merchantName;

}
