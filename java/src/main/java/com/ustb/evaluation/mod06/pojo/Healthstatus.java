package com.ustb.evaluation.mod06.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dic_healthstatus")
public class Healthstatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 健康码编号
     */
    @TableId(value = "fstatus_id", type = IdType.AUTO)
    private Long fstatusId;

    /**
     * 健康码
     */
    private String fstatusName;


}
