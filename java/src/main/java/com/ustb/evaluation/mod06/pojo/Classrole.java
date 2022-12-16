package com.ustb.evaluation.mod06.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dic_classrole")
public class Classrole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级干部编号
     */
    @TableId(value = "fclass_role_id", type = IdType.AUTO)
    private Long fclassRoleId;

    /**
     * 班级干部名称
     */
    @TableField("class_role_name")
    private String classRoleName;

    /**
     * 备注
     */
    private String memo;


}
