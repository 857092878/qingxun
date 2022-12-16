package com.ustb.evaluation.mod06.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dic_healthgrade")
public class Healthgrade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 健康等级编号
     */
    @TableId(value = "fhealth_id", type = IdType.AUTO)
    private Long fhealthId;

    /**
     * 健康等级
     */
    private String fhealth;


}
