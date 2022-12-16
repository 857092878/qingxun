package com.ustb.evaluation.mod06.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
@TableName("stu_acid")
public class Acid implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    private Long fstudentId;

    /**
     * 采样时间
     */
    private Date fsampleTime;

    /**
     * 检测时间
     */
    private Date ftestTime;

    /**
     * 检测单位
     */
    private String ftestUnit;

    /**
     * 检测结果
     */
    private String fresult;

    /**
     * 采样地点
     */
    private String ftestAddress;

    /**
     * 备注
     */
    private String memo;


}
