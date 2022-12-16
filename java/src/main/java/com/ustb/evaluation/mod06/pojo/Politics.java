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
@TableName("dic_politics")
public class Politics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 政治面貌编号
     */
    @TableId(value = "fcode", type = IdType.AUTO)
    private Long fcode;

    /**
     * 政治面貌名称
     */
    private String fname;

    /**
     * 排序
     */
    private Integer fsortNumber;

    /**
     * 备注
     */
    private String memo;


}
