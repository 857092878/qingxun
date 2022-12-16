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
@TableName("stu_accommodation")
public class Accommodation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 学生id
     */
    private Long fstudentId;

    /**
     * 宿舍id
     */
    private Long fdormitoryId;

    /**
     * 床位
     */
    private Integer fbed;

    /**
     * 是否在宿舍
     */
    private String fin;

    /**
     * 备注
     */
    private String memo;


}
