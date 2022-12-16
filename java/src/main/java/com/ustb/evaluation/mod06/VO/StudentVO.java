package com.ustb.evaluation.mod06.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 学号
     */
    private String fcode;

    /**
     * 姓名
     */
    private String fname;

    /**
     * 性别
     */
    private String fgender;

    /**
     * 手机
     */
    private String fmobile;

    /**
     * 联系方式
     */
    private String fcontact;

    /**
     * 家庭住址
     */
    private String faddress;

    /**
     * 父亲姓名
     */
    private String ffatherName;

    /**
     * 父亲电话
     */
    private String ffatherMobile;

    /**
     * 父亲备注
     */
    private String ffatherMemo;

    /**
     * 身份证
     */
    private String fidCode;

    /**
     * 政治面貌编号
     */
    private Long fpoliticsId;

    /**
     * 班级干部编号
     */
    private Long fclassRoleId;

    /**
     * 民族编号
     */
    private Long fnationalityId;

    /**
     * 备注
     */
    private String memo;

    private String fname1;

    private String classRoleName;

    private String fnationalityName;



}
