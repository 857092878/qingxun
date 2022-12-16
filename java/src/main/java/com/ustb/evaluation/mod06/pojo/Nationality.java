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
@TableName("dic_nationality")
public class Nationality implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名族编号
     */
    @TableId(value = "fnationality_id", type = IdType.AUTO)
    private Long fnationalityId;

    /**
     * 名族名称
     */
    private String fnationalityName;

    /**
     * 备注
     */
    private String memo;


}
