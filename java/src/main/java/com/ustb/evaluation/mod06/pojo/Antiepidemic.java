package com.ustb.evaluation.mod06.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("stu_antiepidemic")
public class Antiepidemic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    private Long fstudentId;

    /**
     * 是否在校
     */
    private String finCampus;

    /**
     * 目前位置
     */
    private String flocation;

    /**
     * 离校时间
     */
    private LocalDateTime fleaveTime;

    /**
     * 健康等级
     */
    private Long fhealthId;

    /**
     * 健康描述
     */
    private String fhealthMemo;

    /**
     * 健康码
     */
    private Long fstatusId;

    /**
     * 接种完全
     */
    private String fvaccinCompleted;

    /**
     * 接种描述
     */
    private String fvaccinMemo;

    /**
     * 行程
     */
    private String fjourney;

    /**
     * 备注
     */
    private String memo;


}
